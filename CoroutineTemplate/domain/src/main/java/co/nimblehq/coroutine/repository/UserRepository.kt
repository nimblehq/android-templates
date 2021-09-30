package co.nimblehq.coroutine.repository

import co.nimblehq.coroutine.entity.UserEntity
import co.nimblehq.coroutine.usecase.UseCaseResult

interface UserRepository {

    suspend fun getUsers(): UseCaseResult<List<UserEntity>>
}
