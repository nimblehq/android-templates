package co.nimblehq.template.domain.repository

import co.nimblehq.template.domain.model.Model

interface Repository {

    suspend fun getModels(): List<Model>
}
