package co.nimblehq.coroutine.usecase

import co.nimblehq.coroutine.entity.UserEntity
import co.nimblehq.coroutine.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(): UseCaseResult<List<UserEntity>> {
        return try {
            val response = userRepository.getUsers()
            UseCaseResult.Success(response)
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }
}
