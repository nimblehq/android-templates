package co.nimblehq.template.compose.domain.usecases

import co.nimblehq.template.compose.domain.models.Model
import co.nimblehq.template.compose.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(): Flow<List<Model>> {
        return repository.getModels()
    }
}
