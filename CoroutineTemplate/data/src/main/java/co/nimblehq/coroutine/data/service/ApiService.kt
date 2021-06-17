package co.nimblehq.coroutine.data.service

import co.nimblehq.coroutine.data.service.response.UserResponse
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}
