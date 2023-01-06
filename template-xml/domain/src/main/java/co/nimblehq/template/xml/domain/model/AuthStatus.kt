package co.nimblehq.template.xml.domain.model

sealed class AuthStatus {

    data class Authenticated(
        val accessToken: String,
        val refreshToken: String,
        val status: String,
        val tokenType: String?
    )
}
