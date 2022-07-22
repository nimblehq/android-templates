package co.nimblehq.template.data.service

import co.nimblehq.template.data.response.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getResponses(): List<Response>
}
