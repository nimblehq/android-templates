package co.nimblehq.sample.compose.data.remote.models.responses

import co.nimblehq.sample.compose.domain.models.Model
import com.squareup.moshi.Json

data class Response(
    @Json(name = "id")
    val id: Int?,
    // A sample to ensure @Json annotation work properly with a differ between field name and JSON key name
    @Json(name = "username")
    val username: String?,
)

fun Response.toModel() = Model(
    id = this.id,
    username = this.username,
)

fun List<Response>.toModels() = this.map { it.toModel() }
