package co.nimblehq.template.xml.data.repository

import co.nimblehq.template.xml.data.extensions.flowTransform
import co.nimblehq.template.xml.data.response.toModels
import co.nimblehq.template.xml.data.service.ApiService
import co.nimblehq.template.xml.domain.model.Model
import co.nimblehq.template.xml.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl constructor(
    private val apiService: ApiService
) : Repository {

    override fun getModels(): Flow<List<Model>> = flowTransform {
        apiService.getResponses().toModels()
    }
}
