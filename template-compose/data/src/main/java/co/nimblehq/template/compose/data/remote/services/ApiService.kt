package co.nimblehq.template.compose.data.remote.services

import co.nimblehq.template.compose.data.remote.models.responses.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getResponses(): List<Response>
}
