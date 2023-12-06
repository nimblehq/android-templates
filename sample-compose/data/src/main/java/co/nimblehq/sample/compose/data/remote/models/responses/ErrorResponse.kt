package co.nimblehq.sample.compose.data.remote.models.responses

import co.nimblehq.sample.compose.domain.models.Error
import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "message")
    val message: String
)

internal fun ErrorResponse.toModel() = Error(message = message)
