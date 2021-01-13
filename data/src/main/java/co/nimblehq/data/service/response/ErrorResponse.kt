package co.nimblehq.data.service.response

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "message")
    val message: String
)
