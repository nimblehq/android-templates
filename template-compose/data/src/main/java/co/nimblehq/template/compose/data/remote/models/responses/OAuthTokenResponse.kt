package co.nimblehq.template.compose.data.remote.models.responses

import co.nimblehq.template.compose.domain.models.OAuthTokenModel
import com.squareup.moshi.Json

data class OAuthTokenResponse(
    @Json(name = "accessToken")
    val accessToken: String?,
    @Json(name = "tokenType")
    val tokenType: String?,
    @Json(name = "expiresIn")
    val expiresIn: Int?,
    @Json(name = "refreshToken")
    val refreshToken: String?,
)

internal fun OAuthTokenResponse.toModel() = OAuthTokenModel(
    accessToken = accessToken,
    tokenType = tokenType,
    expiresIn = expiresIn,
    refreshToken = refreshToken,
)
