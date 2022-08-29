package co.nimblehq.sample.xml.data.repository

import co.nimblehq.sample.xml.data.response.toModels
import co.nimblehq.sample.xml.data.service.ApiService
import co.nimblehq.sample.xml.domain.model.Model
import co.nimblehq.sample.xml.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl constructor(
    private val apiService: ApiService
) : Repository {

    override fun getModels(): Flow<List<Model>> = flow {
        try {
            val result = apiService.getResponses().toModels()
            emit(result)
        } catch (e: Exception) {
            throw e
        }
    }
}
