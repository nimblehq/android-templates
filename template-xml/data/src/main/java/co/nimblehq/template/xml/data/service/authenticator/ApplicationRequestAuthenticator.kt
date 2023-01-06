package co.nimblehq.template.xml.data.service.authenticator

import android.annotation.SuppressLint
import android.util.Log
import co.nimblehq.template.xml.data.extensions.parseErrorResponse
import co.nimblehq.template.xml.data.service.SessionManager
import co.nimblehq.template.xml.domain.exceptions.NoConnectivityException
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.last
import okhttp3.*

const val REQUEST_HEADER_AUTHORIZATION = "Authorization"

class ApplicationRequestAuthenticator(
    private val tokenRefresher: TokenRefresher,
    private val sessionManager: SessionManager
) : Authenticator {

    lateinit var okHttpClient: OkHttpClient

    private var retryCount = 0

    @SuppressLint("CheckResult", "LongMethod", "TooGenericExceptionCaught")
    override fun authenticate(route: Route?, response: Response): Request? =
        runBlocking {
            if (shouldSkipAuthenticationByErrorType(response)) {
                return@runBlocking null
            }

            // Due to unable to check the last retry succeeded
            // So reset the retry count on the request first triggered by an automatic retry
            if (response.priorResponse == null && retryCount != 0) {
                retryCount = 0
            }

            if (retryCount >= MAX_ATTEMPTS) {
                // Reset retry count once reached max attempts
                retryCount = 0
                return@runBlocking null
            } else {
                retryCount++

                val failedAccessToken = sessionManager.getAccessToken()

                try {
                    val refreshTokenResponse = tokenRefresher.refreshToken().last()
                    val newAccessToken = refreshTokenResponse.accessToken

                    if (newAccessToken.isEmpty() || newAccessToken == failedAccessToken) {
                        // Avoid infinite loop if the new Token == old (failed) token
                        return@runBlocking null
                    }

                    // Update the Interceptor (for future requests)
                    sessionManager.refresh(refreshTokenResponse)

                    // Retry this failed request (401) with the new token
                    return@runBlocking response.request
                        .newBuilder()
                        .header(REQUEST_HEADER_AUTHORIZATION, newAccessToken)
                        .build()
                } catch (e: Exception) {
                    Log.w("AUTHENTICATOR", "Failed to refresh token: $e")
                    return@runBlocking if (e !is NoConnectivityException) {
                        // cancel all pending requests
                        okHttpClient.dispatcher.cancelAll()
                        response.request
                    } else {
                        // do nothing
                        null
                    }
                }
            }
        }

    private fun shouldSkipAuthenticationByErrorType(response: Response): Boolean {
        val headers = response.request.headers
        val skippingError = headers[HEADER_AUTHENTICATION_SKIPPING_ERROR_TYPE]

        if (!skippingError.isNullOrEmpty()) {
            // Clone response body
            // https://github.com/square/okhttp/issues/1240#issuecomment-330813274
            val responseBody = response.peekBody(Long.MAX_VALUE).toString()
            val error = parseErrorResponse(responseBody)

            return error != null && skippingError == error.type
        }
        return false
    }
}

const val HEADER_AUTHENTICATION_SKIPPING_ERROR_TYPE = "Authentication-Skipping-ErrorType"
private const val MAX_ATTEMPTS = 3
