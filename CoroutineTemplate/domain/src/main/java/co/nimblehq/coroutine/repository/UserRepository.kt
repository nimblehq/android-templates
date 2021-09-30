package co.nimblehq.coroutine.repository

import co.nimblehq.coroutine.entity.UserEntity

interface UserRepository {

    suspend fun getUsers(): List<UserEntity>
}
