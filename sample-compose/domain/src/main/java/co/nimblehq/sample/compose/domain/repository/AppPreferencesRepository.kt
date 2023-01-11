package co.nimblehq.sample.compose.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {

    fun getFirstTimeLaunchPreferences(): Flow<Boolean>

    suspend fun updateFirstTimeLaunchPreferences(isFirstTimeLaunch: Boolean)
}

