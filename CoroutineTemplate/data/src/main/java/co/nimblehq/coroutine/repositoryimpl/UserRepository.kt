package co.nimblehq.coroutine.repositoryimpl

import co.nimblehq.coroutine.data.service.ApiService
import co.nimblehq.coroutine.entity.UserEntity
import co.nimblehq.coroutine.repository.UserRepository

class UserRepositoryImpl constructor(
    private val apiService: ApiService,
) : UserRepository {

    override suspend fun getUsers(): List<UserEntity> {
        return apiService.getUsers()
    }
}
