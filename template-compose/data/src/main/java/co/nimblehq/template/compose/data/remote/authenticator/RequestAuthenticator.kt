package co.nimblehq.template.compose.data.remote.authenticator

import co.nimblehq.template.compose.data.remote.interceptor.AUTH_HEADER
import co.nimblehq.template.compose.data.util.DispatchersProvider
import co.nimblehq.template.compose.domain.exceptions.NoConnectivityException
import co.nimblehq.template.compose.domain.models.AuthStatus
import co.nimblehq.template.compose.domain.models.toAuthenticatedStatus
import co.nimblehq.template.compose.domain.usecases.GetAuthStatusUseCase
import co.nimblehq.template.compose.domain.usecases.RefreshTokenUseCase
import co.nimblehq.template.compose.domain.usecases.UpdateLoginTokensUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import kotlin.math.pow

private const val MAX_ATTEMPTS = 3
private const val EXPONENTIAL_BACKOFF_BASE = 2.0
private const val EXPONENTIAL_BACKOFF_SCALE = 0.5
private const val SECOND_IN_MILLISECONDS = 1000

class RequestAuthenticator(
    private val dispatchersProvider: DispatchersProvider,
    private val getAuthStatusUseCase: GetAuthStatusUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val updateLoginTokensUseCase: UpdateLoginTokensUseCase,
) : Authenticator {

    var okHttpClient: OkHttpClient? = null
    private var retryCount = 0
    private var retryDelay = 0L

    override fun authenticate(route: Route?, response: Response): Request? {
        // Due to unable to check the last retry succeeded
        // So reset the retry count on the request first triggered by an automatic retry
        if (response.priorResponse == null && retryCount != 0) {
            retryCount = 0
        }

        return if (retryCount >= MAX_ATTEMPTS) {
            // Reset retry count once reached max attempts
            retryCount = 0
            null
        } else {
            retryCount++

            retryDelay = calculateExponentialBackoff(retryCount).toLong()
            runBlocking { delay(retryDelay * SECOND_IN_MILLISECONDS) }

            retryWithNewToken(response)
        }
    }

    @Suppress("TooGenericExceptionCaught", "NestedBlockDepth")
    @Synchronized
    private fun retryWithNewToken(response: Response): Request? = try {
        val (tokenType, accessToken) = run {
            val authStatus = getAuthStatus() as? AuthStatus.Authenticated
            val tokenType = authStatus?.tokenType.orEmpty()
            val accessToken = authStatus?.accessToken.orEmpty()
            val refreshToken = authStatus?.refreshToken.orEmpty()

            val headerAccessToken = response.request.header(AUTH_HEADER)?.removePrefix(tokenType)?.trim()

            if (accessToken == headerAccessToken) {
                val newAuthStatus = refreshAccessToken(refreshToken)
                val newTokenType = newAuthStatus?.tokenType.orEmpty()
                val newAccessToken = newAuthStatus?.accessToken.orEmpty()

                if (newAccessToken.isEmpty() || newAccessToken == accessToken) {
                    // Avoid infinite loop if the new Token == old (failed) token
                    return null
                }

                // Update the token (for future requests)
                newAuthStatus?.let(::updateToken)

                // Retry with the NEW token
                newTokenType to newAccessToken
            } else {
                // Retry with the EXISTING token
                tokenType to accessToken
            }
        }

        response.newRequestWithToken(
            tokenType = tokenType,
            accessToken = accessToken
        )
    } catch (e: Exception) {
        if (e !is NoConnectivityException) {
            // send force logout request to view layer
            RequestInterceptingDelegate.requestForceLogout()

            // cancel all pending requests
            okHttpClient?.dispatcher?.cancelAll()
            response.request
        } else {
            // do nothing
            null
        }
    }

    private fun calculateExponentialBackoff(retryCount: Int): Double {
        return EXPONENTIAL_BACKOFF_BASE.pow(retryCount.toDouble()) * EXPONENTIAL_BACKOFF_SCALE
    }

    private fun getAuthStatus(): AuthStatus? = runBlocking(dispatchersProvider.io) {
        getAuthStatusUseCase().firstOrNull()
    }

    private fun updateToken(authStatus: AuthStatus) = runBlocking(dispatchersProvider.io) {
        updateLoginTokensUseCase(authStatus).collect()
    }

    private fun refreshAccessToken(refreshToken: String): AuthStatus.Authenticated? =
        runBlocking(dispatchersProvider.io) {
            refreshTokenUseCase(refreshToken).firstOrNull()?.toAuthenticatedStatus()
        }

    private fun Response.newRequestWithToken(tokenType: String, accessToken: String): Request =
        request.newBuilder()
            .header(AUTH_HEADER, "$tokenType $accessToken")
            .build()
}
