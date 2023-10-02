package co.nimblehq.template.compose.data.services

import co.nimblehq.template.compose.data.responses.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getResponses(): List<Response>
}
