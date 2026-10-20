package co.nimblehq.template.compose.domain.models

sealed class AuthStatus {

    data class Authenticated(
        val accessToken: String,
        val tokenType: String?,
        val expiresIn: Int?,
        val refreshToken: String?,
    ) : AuthStatus()

    data object Anonymous : AuthStatus()
}

fun OAuthTokenModel.toAuthenticatedStatus() = AuthStatus.Authenticated(
    accessToken = accessToken.orEmpty(),
    tokenType = tokenType,
    expiresIn = expiresIn,
    refreshToken = refreshToken.orEmpty()
)

val AuthStatus.isAuthenticated: Boolean
    get() = this is AuthStatus.Authenticated
