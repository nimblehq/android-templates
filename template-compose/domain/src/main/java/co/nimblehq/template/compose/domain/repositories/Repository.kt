package co.nimblehq.template.compose.domain.repositories

import co.nimblehq.template.compose.domain.models.Model
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getModels(): Flow<List<Model>>
}
