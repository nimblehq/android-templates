package co.nimblehq.sample.compose.data.responses

import co.nimblehq.sample.compose.domain.models.Model
import com.squareup.moshi.Json

data class Response(
    @Json(name = "id")
    val id: Int?,
    // A sample to ensure @Json annotation work properly with a differ between field name and JSON key name
    @Json(name = "username")
    val userName: String?,
)

private fun Response.toModel() = Model(
    id = this.id,
    username = this.userName,
)

fun List<Response>.toModels() = this.map { it.toModel() }
