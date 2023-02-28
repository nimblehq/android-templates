package co.nimblehq.template.compose.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {

    fun getAppPreferences(): Flow<Boolean>

    suspend fun updateAppPreferences(appPreferencesValue: Boolean)
}
