package co.nimblehq.template.compose.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import co.nimblehq.template.compose.domain.model.UserPreferences
import co.nimblehq.template.compose.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    UserPreferencesRepository {

    private val TAG: String = "UserPreferencesRepo"

    private object PreferencesKeys {
        val USERNAME = stringPreferencesKey("username")
        val IS_ACTIVE = booleanPreferencesKey("is_active")
    }

    override fun getUsernamePreferences(): Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    override suspend fun updateUsernamePreferences(usernamePreferences: UserPreferences) {
        dataStore.edit { preferences ->
            val currentUserPreferences = mapUserPreferences(preferences)

            if (usernamePreferences.username != currentUserPreferences.username) {
                preferences[PreferencesKeys.USERNAME] = usernamePreferences.username
            }

            if (usernamePreferences.isActive != currentUserPreferences.isActive) {
                preferences[PreferencesKeys.IS_ACTIVE] = usernamePreferences.isActive
            }
        }
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val username = preferences[PreferencesKeys.USERNAME] ?: ""
        val isActive = preferences[PreferencesKeys.IS_ACTIVE] ?: false
        return UserPreferences(username, isActive)
    }
}
