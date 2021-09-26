package co.nimblehq.coroutine.repositoryimpl

import co.nimblehq.coroutine.data.service.ApiService
import co.nimblehq.coroutine.data.service.response.toUserEntities
import co.nimblehq.domain.entity.UserEntity
import co.nimblehq.domain.repository.UserRepository
import co.nimblehq.domain.usecase.UseCaseResult

class UserRepositoryImpl constructor(
    private val apiService: ApiService,
) : UserRepository {

    override suspend fun getUsers(page: Int, size: Int): UseCaseResult<List<UserEntity>> {
        return try {
            val response = apiService.getUsers()
            UseCaseResult.Success(response.toUserEntities())
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }
}
