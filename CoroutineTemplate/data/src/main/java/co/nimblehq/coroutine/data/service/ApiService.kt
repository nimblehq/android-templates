package co.nimblehq.coroutine.data.service

import co.nimblehq.coroutine.entity.UserEntity
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<UserEntity>
}
