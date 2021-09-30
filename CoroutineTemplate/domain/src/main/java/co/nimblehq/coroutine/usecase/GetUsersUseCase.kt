package co.nimblehq.coroutine.usecase

import co.nimblehq.coroutine.entity.UserEntity
import co.nimblehq.coroutine.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(): UseCaseResult<List<UserEntity>> {
        return userRepository.getUsers()
    }
}
