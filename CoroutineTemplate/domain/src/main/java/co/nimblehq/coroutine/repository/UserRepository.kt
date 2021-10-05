package co.nimblehq.coroutine.repository

import co.nimblehq.coroutine.model.User

interface UserRepository {

    suspend fun getUsers(): List<User>
}
