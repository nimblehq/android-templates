package co.nimblehq.coroutine.repository

import co.nimblehq.coroutine.response.UserResponse

interface UserRepository {

    suspend fun getUsers(): List<UserResponse>
}
