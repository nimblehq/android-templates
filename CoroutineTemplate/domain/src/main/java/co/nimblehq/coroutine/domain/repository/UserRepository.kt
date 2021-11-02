package co.nimblehq.coroutine.domain.repository

import co.nimblehq.coroutine.domain.model.User

interface UserRepository {

    suspend fun getUsers(): List<User>
}
