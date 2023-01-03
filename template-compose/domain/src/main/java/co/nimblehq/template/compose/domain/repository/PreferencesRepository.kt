package co.nimblehq.template.compose.domain.repository

import co.nimblehq.template.compose.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    fun getUserPreferences(): Flow<UserPreferences>

    suspend fun updateUserPreferences(userPreferences: UserPreferences)
}
