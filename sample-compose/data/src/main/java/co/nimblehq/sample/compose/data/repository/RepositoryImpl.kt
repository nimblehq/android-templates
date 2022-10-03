package co.nimblehq.sample.compose.data.repository

import co.nimblehq.sample.compose.data.extensions.flowTransform
import co.nimblehq.sample.compose.data.response.toModels
import co.nimblehq.sample.compose.data.service.ApiService
import co.nimblehq.sample.compose.domain.model.Model
import co.nimblehq.sample.compose.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl constructor(
    private val apiService: ApiService
) : Repository {

    override fun getModels(): Flow<List<Model>> = flowTransform {
        apiService.getResponses().toModels()
    }
}
