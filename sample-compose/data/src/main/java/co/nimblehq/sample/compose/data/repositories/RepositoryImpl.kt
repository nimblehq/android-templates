package co.nimblehq.sample.compose.data.repositories

import co.nimblehq.sample.compose.data.extensions.flowTransform
import co.nimblehq.sample.compose.data.remote.models.responses.toModels
import co.nimblehq.sample.compose.data.remote.services.ApiService
import co.nimblehq.sample.compose.domain.models.Model
import co.nimblehq.sample.compose.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl constructor(
    private val apiService: ApiService
) : Repository {

    override fun getModels(): Flow<List<Model>> = flowTransform {
        apiService.getResponses().toModels()
    }
}
