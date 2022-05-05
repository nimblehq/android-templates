package co.nimblehq.sample.compose.domain.repository

import co.nimblehq.sample.compose.domain.model.Model
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getModels(): Flow<List<Model>>
}
