package co.nimblehq.template.compose.data.service

import co.nimblehq.template.compose.data.response.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getResponses(): List<Response>
}
