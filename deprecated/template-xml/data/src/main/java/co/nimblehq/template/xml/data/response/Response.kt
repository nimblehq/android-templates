package co.nimblehq.template.xml.data.response

import co.nimblehq.template.xml.domain.model.Model
import com.squareup.moshi.Json

data class Response(
    @Json(name = "id") val id: Int?
)

private fun Response.toModel() = Model(id = this.id)

fun List<Response>.toModels() = this.map { it.toModel() }
