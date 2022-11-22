package co.nimblehq.template.compose.domain.usecase

import co.nimblehq.template.compose.domain.model.Model
import co.nimblehq.template.compose.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(): Flow<List<Model>> {
        return repository.getModels()
    }
}
