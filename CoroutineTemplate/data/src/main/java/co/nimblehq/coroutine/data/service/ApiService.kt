package co.nimblehq.coroutine.data.service

import co.nimblehq.coroutine.data.response.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getResponses(): List<Response>
}
