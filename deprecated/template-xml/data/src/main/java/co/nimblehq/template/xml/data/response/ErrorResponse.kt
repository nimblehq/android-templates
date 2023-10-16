package co.nimblehq.template.xml.data.response

import co.nimblehq.template.xml.domain.model.Error
import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "message")
    val message: String
)

internal fun ErrorResponse.toModel() = Error(message = message)
