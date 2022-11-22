package co.nimblehq.template.compose.domain.repository

import co.nimblehq.template.compose.domain.model.Model
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getModels(): Flow<List<Model>>
}
