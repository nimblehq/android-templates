package co.nimblehq.coroutine.repositoryimpl

import co.nimblehq.coroutine.data.service.ApiService
import co.nimblehq.coroutine.model.User
import co.nimblehq.coroutine.repository.UserRepository
import co.nimblehq.coroutine.response.toUsers

class UserRepositoryImpl constructor(
    private val apiService: ApiService,
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers().toUsers()
    }
}
