package co.nimblehq.sample.xml.data.service

import co.nimblehq.sample.xml.data.response.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getResponses(): List<Response>
}
