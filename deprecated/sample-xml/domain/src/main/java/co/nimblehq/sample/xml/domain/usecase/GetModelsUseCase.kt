package co.nimblehq.sample.xml.domain.usecase

import co.nimblehq.sample.xml.domain.model.Model
import co.nimblehq.sample.xml.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetModelsUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(): Flow<List<Model>> {
        return repository.getModels()
    }
}
