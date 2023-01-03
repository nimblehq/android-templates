package co.nimblehq.template.compose.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import co.nimblehq.template.compose.domain.model.UserPreferences
import co.nimblehq.template.compose.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    PreferencesRepository {

    private object PreferencesKeys {
        val ID = stringPreferencesKey("ID")
    }

    override fun getUserPreferences(): Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG_PREFERENCES, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    override suspend fun updateUserPreferences(userPreferences: UserPreferences) {
        dataStore.edit { preferences ->
            val currentUserPreferences = mapUserPreferences(preferences)

            if (userPreferences.id != currentUserPreferences.id) {
                preferences[PreferencesKeys.ID] = userPreferences.id
            }
        }
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val id = preferences[PreferencesKeys.ID] ?: ""
        return UserPreferences(id)
    }

    companion object {
        private const val TAG_PREFERENCES: String = "PreferencesRepo"
    }
}
