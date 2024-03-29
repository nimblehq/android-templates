package co.nimblehq.template.xml.domain.usecase

import co.nimblehq.template.xml.domain.model.Model
import co.nimblehq.template.xml.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(): Flow<List<Model>> {
        return repository.getModels()
    }
}
