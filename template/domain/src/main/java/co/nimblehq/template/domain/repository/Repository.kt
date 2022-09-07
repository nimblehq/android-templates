package co.nimblehq.template.domain.repository

import co.nimblehq.template.domain.model.Model
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getModels(): Flow<List<Model>>
}
