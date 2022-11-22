package co.nimblehq.template.compose.data.repository

import co.nimblehq.template.compose.data.extensions.flowTransform
import co.nimblehq.template.compose.data.response.toModels
import co.nimblehq.template.compose.data.service.ApiService
import co.nimblehq.template.compose.domain.model.Model
import co.nimblehq.template.compose.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl constructor(
    private val apiService: ApiService
) : Repository {

    override fun getModels(): Flow<List<Model>> = flowTransform {
        apiService.getResponses().toModels()
    }
}
