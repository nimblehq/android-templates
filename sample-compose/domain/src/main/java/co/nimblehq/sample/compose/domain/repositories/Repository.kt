package co.nimblehq.sample.compose.domain.repositories

import co.nimblehq.sample.compose.domain.models.Model
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getModels(): Flow<List<Model>>

    fun getDetails(id: Int): Flow<Model>
}
