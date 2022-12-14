package co.nimblehq.template.compose.domain.repository

import co.nimblehq.template.compose.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getUsernamePreferences(): Flow<UserPreferences>

    suspend fun updateUsernamePreferences(usernamePreferences: UserPreferences)
}
