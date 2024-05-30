package co.nimblehq.template.compose.domain.models

data class OAuthTokenModel(
    val accessToken: String?,
    val tokenType: String?,
    val expiresIn: Int?,
    val refreshToken: String?,
)
