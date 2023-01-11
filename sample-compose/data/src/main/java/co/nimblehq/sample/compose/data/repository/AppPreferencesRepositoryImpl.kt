package co.nimblehq.sample.compose.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import co.nimblehq.sample.compose.domain.repository.AppPreferencesRepository
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

class AppPreferencesRepositoryImpl @Inject constructor(
    private val appPreferencesDataStore: DataStore<Preferences>
) : AppPreferencesRepository {

    private object PreferencesKeys {
        val FIRST_TIME_LAUNCH = booleanPreferencesKey("FIRST_TIME_LAUNCH")
    }

    override fun getFirstTimeLaunchPreferences(): Flow<Boolean> = appPreferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(
                    AppPreferencesRepositoryImpl::class.simpleName,
                    "Error reading preferences.",
                    exception
                )
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.FIRST_TIME_LAUNCH] ?: true
        }

    override suspend fun updateFirstTimeLaunchPreferences(isFirstTimeLaunch: Boolean) {
        appPreferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.FIRST_TIME_LAUNCH] = isFirstTimeLaunch
        }
    }
}
