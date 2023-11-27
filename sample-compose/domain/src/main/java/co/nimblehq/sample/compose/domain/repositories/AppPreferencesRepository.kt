package co.nimblehq.sample.compose.domain.repositories

import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {

    fun isFirstTimeLaunch(): Flow<Boolean>

    suspend fun updateFirstTimeLaunch(isFirstTimeLaunch: Boolean)
}
