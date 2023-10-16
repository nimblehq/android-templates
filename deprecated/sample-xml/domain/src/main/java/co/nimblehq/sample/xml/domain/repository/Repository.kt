package co.nimblehq.sample.xml.domain.repository

import co.nimblehq.sample.xml.domain.model.Model
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getModels(): Flow<List<Model>>
}
