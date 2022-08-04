package co.nimblehq.sample.xml.data.repository

import co.nimblehq.sample.xml.data.response.toModels
import co.nimblehq.sample.xml.data.service.ApiService
import co.nimblehq.sample.xml.domain.model.Model
import co.nimblehq.sample.xml.domain.repository.Repository

class RepositoryImpl constructor(
    private val apiService: ApiService
) : Repository {

    override suspend fun getModels(): List<Model> = apiService.getResponses().toModels()
}
