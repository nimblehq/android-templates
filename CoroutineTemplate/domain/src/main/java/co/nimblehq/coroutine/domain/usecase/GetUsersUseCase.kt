package co.nimblehq.coroutine.domain.usecase

import co.nimblehq.coroutine.domain.data.entity.UserEntity
import co.nimblehq.coroutine.domain.dispatcher.IoDispatcher
import co.nimblehq.coroutine.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun execute(): UseCaseResult<List<UserEntity>> {
        return withContext(ioDispatcher) {
            try {
                val result = userRepository.getUsers()
                UseCaseResult.Success(result)
            } catch (exception: Exception) {
                UseCaseResult.Error(exception)
            }
        }
    }
}
