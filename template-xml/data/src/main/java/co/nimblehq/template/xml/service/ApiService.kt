package co.nimblehq.template.xml.service

import co.nimblehq.template.xml.response.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getResponses(): List<Response>
}
