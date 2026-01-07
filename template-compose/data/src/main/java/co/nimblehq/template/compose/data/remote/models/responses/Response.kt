package co.nimblehq.template.compose.data.remote.models.responses

import co.nimblehq.template.compose.domain.models.Model
import com.squareup.moshi.Json

data class Response(
    @Json(name = "id") val id: Int?,
)

private fun Response.toModel() = Model(id = this.id)

fun List<Response>.toModels() = this.map { it.toModel() }
