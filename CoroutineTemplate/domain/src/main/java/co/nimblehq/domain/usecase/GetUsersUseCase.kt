package co.nimblehq.domain.usecase

import co.nimblehq.domain.entity.UserEntity
import co.nimblehq.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(page: Int, size: Int): UseCaseResult<List<UserEntity>> {
        return userRepository.getUsers(page, size)
    }
}
