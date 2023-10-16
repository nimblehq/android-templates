package co.nimblehq.template.xml.domain.repository

import co.nimblehq.template.xml.domain.model.Model
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getModels(): Flow<List<Model>>
}
