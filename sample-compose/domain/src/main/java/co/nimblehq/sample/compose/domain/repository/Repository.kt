package co.nimblehq.sample.compose.domain.repository

import co.nimblehq.sample.compose.domain.model.Model

interface Repository {

    suspend fun getModels(): List<Model>
}
