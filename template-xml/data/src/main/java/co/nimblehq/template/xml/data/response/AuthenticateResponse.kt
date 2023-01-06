package co.nimblehq.template.xml.data.response

import co.nimblehq.template.xml.domain.model.AuthStatus
import com.squareup.moshi.Json

data class AuthenticateResponse(
    @Json(name = "access_token")
    val accessToken: String,
    @Json(name = "refresh_token")
    val refreshToken: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "token_type")
    val tokenType: String?
)

fun AuthenticateResponse.toAuthenticated() = AuthStatus.Authenticated(
    accessToken = accessToken,
    refreshToken = refreshToken,
    status = status,
    tokenType = tokenType
)
