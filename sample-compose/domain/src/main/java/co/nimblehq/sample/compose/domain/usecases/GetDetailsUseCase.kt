package co.nimblehq.sample.compose.domain.usecases

import co.nimblehq.sample.compose.domain.models.Model
import co.nimblehq.sample.compose.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(id: Int): Flow<Model> {
        return repository.getDetails(id)
    }
}
