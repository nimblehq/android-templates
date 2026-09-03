package co.nimblehq.template.compose.data.remote.interceptor

import co.nimblehq.template.compose.data.local.preferences.NetworkEncryptedSharedPreferences
import co.nimblehq.template.compose.domain.models.AuthStatus
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

const val AUTH_HEADER = "Authorization"

class AuthInterceptor @Inject constructor(
    private val preferences: NetworkEncryptedSharedPreferences,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (preferences.authStatus is AuthStatus.Authenticated) {
            val authStatus = preferences.authStatus as AuthStatus.Authenticated
            chain.request()
                .newBuilder()
                .header(
                    name = AUTH_HEADER,
                    value = "${authStatus.tokenType} ${authStatus.accessToken}"
                )
                .build()
                .let(chain::proceed)
        } else {
            chain.proceed(chain.request())
        }
    }
}
