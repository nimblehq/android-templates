package co.nimblehq.coroutine.domain.repository

import co.nimblehq.coroutine.domain.model.Model

interface Repository {

    suspend fun getModels(): List<Model>
}
