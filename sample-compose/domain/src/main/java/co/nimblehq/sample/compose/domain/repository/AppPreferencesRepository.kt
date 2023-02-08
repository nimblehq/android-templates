package co.nimblehq.sample.compose.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {

    fun isFirstTimeLaunch(): Flow<Boolean>

    suspend fun updateFirstTimeLaunch(isFirstTimeLaunch: Boolean)
}

