package co.nimblehq.sample.xml.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {

    fun isFirstTimeLaunch(): Flow<Boolean>

    suspend fun updateFirstTimeLaunch(isFirstTimeLaunch: Boolean)
}
