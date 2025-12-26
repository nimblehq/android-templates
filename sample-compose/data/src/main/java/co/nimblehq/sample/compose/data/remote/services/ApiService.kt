package co.nimblehq.sample.compose.data.remote.services

import co.nimblehq.sample.compose.data.remote.models.responses.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users")
    suspend fun getResponses(): List<Response>

    @GET("users/{id}")
    suspend fun getDetails(
        @Path("id") id: Int
    ): Response
}
