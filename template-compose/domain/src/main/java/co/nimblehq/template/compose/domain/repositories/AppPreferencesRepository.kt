package co.nimblehq.template.compose.domain.repositories

import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {
    fun getAppPreference(): Flow<Boolean>

    suspend fun updateAppPreference(appPreferencesValue: Boolean)
}
