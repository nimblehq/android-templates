package co.nimblehq.coroutine.repositoryimpl

import co.nimblehq.coroutine.data.service.ApiService
import co.nimblehq.coroutine.response.UserResponse
import co.nimblehq.coroutine.repository.UserRepository

class UserRepositoryImpl constructor(
    private val apiService: ApiService,
) : UserRepository {

    override suspend fun getUsers(): List<UserResponse> {
        return apiService.getUsers()
    }
}
