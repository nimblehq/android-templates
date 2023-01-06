package co.nimblehq.sample.compose.data.repository

import co.nimblehq.sample.compose.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    fun getUserPreferences(): Flow<UserPreferences>

    suspend fun updateUserPreferences(userPreferences: UserPreferences)
}
