package co.nimblehq.template.xml.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {

    fun getAppPreference(): Flow<Boolean>

    suspend fun updateAppPreference(appPreferenceValue: Boolean)
}
