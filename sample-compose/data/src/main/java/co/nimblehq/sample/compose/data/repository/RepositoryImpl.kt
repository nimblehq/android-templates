package co.nimblehq.sample.compose.data.repository

import co.nimblehq.sample.compose.data.response.toModels
import co.nimblehq.sample.compose.data.service.ApiService
import co.nimblehq.sample.compose.domain.model.Model
import co.nimblehq.sample.compose.domain.repository.Repository

class RepositoryImpl constructor(
    private val apiService: ApiService
) : Repository {

    override suspend fun getModels(): List<Model> = apiService.getResponses().toModels()
}
