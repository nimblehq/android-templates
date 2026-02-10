package co.nimblehq.template.compose.data.repositories

import co.nimblehq.template.compose.data.extensions.flowTransform
import co.nimblehq.template.compose.data.remote.models.responses.toModels
import co.nimblehq.template.compose.data.remote.services.ApiService
import co.nimblehq.template.compose.domain.models.Model
import co.nimblehq.template.compose.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val apiService: ApiService
) : Repository {

    override fun getModels(): Flow<List<Model>> = flowTransform {
        apiService.getResponses().toModels()
    }
}
