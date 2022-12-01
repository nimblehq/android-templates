package co.nimblehq.template.compose.data.response

import co.nimblehq.template.compose.domain.model.Error
import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "message")
    val message: String
)

internal fun ErrorResponse.toModel() = Error(message = message)
