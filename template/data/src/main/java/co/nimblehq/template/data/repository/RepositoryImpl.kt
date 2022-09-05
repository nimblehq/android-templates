package co.nimblehq.template.data.repository

import co.nimblehq.template.data.response.toModels
import co.nimblehq.template.data.service.ApiService
import co.nimblehq.template.domain.model.Model
import co.nimblehq.template.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl constructor(
    private val apiService: ApiService
) : Repository {

    override fun getModels(): Flow<List<Model>> = flow {
        try {
            val result = apiService.getResponses().toModels()
            emit(result)
        } catch (exception: Exception) {
            // TODO do error mapping here
            throw exception
        }
    }
}
