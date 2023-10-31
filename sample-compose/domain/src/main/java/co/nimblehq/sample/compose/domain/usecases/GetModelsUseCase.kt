package co.nimblehq.sample.compose.domain.usecases

import co.nimblehq.sample.compose.domain.models.Model
import co.nimblehq.sample.compose.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetModelsUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(): Flow<List<Model>> {
        return repository.getModels()
    }
}
