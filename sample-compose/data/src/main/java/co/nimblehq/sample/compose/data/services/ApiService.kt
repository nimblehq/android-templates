package co.nimblehq.sample.compose.data.services

import co.nimblehq.sample.compose.data.responses.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getResponses(): List<Response>
}
