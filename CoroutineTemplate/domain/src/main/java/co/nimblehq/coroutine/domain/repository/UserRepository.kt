package co.nimblehq.coroutine.domain.repository

import co.nimblehq.coroutine.data.service.ApiService
import co.nimblehq.coroutine.domain.data.entity.UserEntity
import co.nimblehq.coroutine.domain.data.entity.toUsersEntity
import javax.inject.Inject

interface UserRepository {

    suspend fun getUsers(): List<UserEntity>
}

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getUsers(): List<UserEntity> {
        val response = apiService.getUsers()
        return response.toUsersEntity()
    }
}
