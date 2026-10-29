package co.nimblehq.template.compose.data.remote.models.requests

import com.squareup.moshi.Json

data class RefreshTokenRequest(
    @Json(name = "grantType")
    val grantType: String,
    @Json(name = "refreshToken")
    val refreshToken: String,
)

internal fun String.toRefreshTokenRequest() = RefreshTokenRequest(
    grantType = "refresh_token",
    refreshToken = this,
)
