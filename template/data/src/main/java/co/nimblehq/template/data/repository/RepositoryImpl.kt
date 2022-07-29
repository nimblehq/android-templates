package co.nimblehq.template.data.repository

import co.nimblehq.template.data.response.toModels
import co.nimblehq.template.data.service.ApiService
import co.nimblehq.template.domain.model.Model
import co.nimblehq.template.domain.repository.Repository

class RepositoryImpl constructor(
    private val apiService: ApiService
) : Repository {

    override suspend fun getModels(): List<Model> = apiService.getResponses().toModels()
}
