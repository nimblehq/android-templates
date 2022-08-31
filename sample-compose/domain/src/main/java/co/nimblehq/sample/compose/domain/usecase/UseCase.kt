package co.nimblehq.sample.compose.domain.usecase

import co.nimblehq.sample.compose.domain.model.Model
import co.nimblehq.sample.compose.domain.repository.Repository
import javax.inject.Inject

class UseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(): UseCaseResult<List<Model>> {
        return try {
            val response = repository.getModels()
            UseCaseResult.Success(response)
        } catch (e: IllegalStateException) {
            UseCaseResult.Error(e)
        }
    }
}
