package co.nimblehq.sample.xml.data.repository

import co.nimblehq.sample.xml.data.extensions.flowTransform
import co.nimblehq.sample.xml.data.response.toModels
import co.nimblehq.sample.xml.data.service.ApiService
import co.nimblehq.sample.xml.domain.model.Model
import co.nimblehq.sample.xml.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl constructor(
    private val apiService: ApiService
) : Repository {

    override fun getModels(): Flow<List<Model>> = flowTransform {
        apiService.getResponses().toModels()
    }
}
