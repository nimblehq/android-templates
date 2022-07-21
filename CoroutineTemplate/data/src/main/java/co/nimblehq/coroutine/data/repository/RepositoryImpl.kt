package co.nimblehq.coroutine.data.repository

import co.nimblehq.coroutine.data.response.toModels
import co.nimblehq.coroutine.data.service.ApiService
import co.nimblehq.coroutine.domain.model.Model
import co.nimblehq.coroutine.domain.repository.Repository

class RepositoryImpl constructor(
    private val apiService: ApiService
) : Repository {

    override suspend fun getModels(): List<Model> = apiService.getResponses().toModels()
}
