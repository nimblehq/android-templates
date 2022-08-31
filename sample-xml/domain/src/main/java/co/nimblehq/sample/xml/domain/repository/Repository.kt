package co.nimblehq.sample.xml.domain.repository

import co.nimblehq.sample.xml.domain.model.Model

interface Repository {

    suspend fun getModels(): List<Model>
}
