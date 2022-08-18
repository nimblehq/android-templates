package co.nimblehq.sample.compose.data.service

import co.nimblehq.sample.compose.data.response.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getResponses(): List<Response>
}
