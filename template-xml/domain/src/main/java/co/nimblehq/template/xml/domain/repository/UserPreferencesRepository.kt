package co.nimblehq.template.xml.domain.repository

import co.nimblehq.template.xml.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getUsernamePreferences(): Flow<UserPreferences>

    suspend fun updateUsernamePreferences(usernamePreferences: UserPreferences)
}
