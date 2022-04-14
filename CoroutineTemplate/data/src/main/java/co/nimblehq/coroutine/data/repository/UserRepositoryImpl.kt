package co.nimblehq.coroutine.data.repository

import co.nimblehq.coroutine.data.response.toUsers
import co.nimblehq.coroutine.data.service.ApiService
import co.nimblehq.coroutine.domain.model.User
import co.nimblehq.coroutine.domain.repository.UserRepository

class UserRepositoryImpl constructor(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers().toUsers()
    }
}
