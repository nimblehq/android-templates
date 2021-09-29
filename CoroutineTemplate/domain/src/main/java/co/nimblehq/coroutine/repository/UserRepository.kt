package co.nimblehq.domain.repository

import co.nimblehq.domain.entity.UserEntity
import co.nimblehq.domain.usecase.UseCaseResult

interface UserRepository {

    suspend fun getUsers(page: Int, size: Int): UseCaseResult<List<UserEntity>>
}
