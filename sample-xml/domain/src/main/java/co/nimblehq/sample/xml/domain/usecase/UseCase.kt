package co.nimblehq.sample.xml.domain.usecase

import co.nimblehq.sample.xml.domain.model.Model
import co.nimblehq.sample.xml.domain.repository.Repository
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
