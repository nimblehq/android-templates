package co.nimblehq.coroutine.domain.usecase

import co.nimblehq.coroutine.domain.model.User
import co.nimblehq.coroutine.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(): UseCaseResult<List<User>> {
        return try {
            val response = userRepository.getUsers()
            UseCaseResult.Success(response)
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }
}
