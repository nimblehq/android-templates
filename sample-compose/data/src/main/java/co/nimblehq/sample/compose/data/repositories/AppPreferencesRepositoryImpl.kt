package co.nimblehq.sample.compose.data.repositories

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import co.nimblehq.sample.compose.domain.repositories.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AppPreferencesRepositoryImpl @Inject constructor(
    private val appPreferencesDataStore: DataStore<Preferences>
) : AppPreferencesRepository {

    private object PreferencesKeys {
        val FIRST_TIME_LAUNCH = booleanPreferencesKey("FIRST_TIME_LAUNCH")
    }

    override fun isFirstTimeLaunch(): Flow<Boolean> = appPreferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.i(
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

    override suspend fun updateFirstTimeLaunch(isFirstTimeLaunch: Boolean) {
        appPreferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.FIRST_TIME_LAUNCH] = isFirstTimeLaunch
        }
    }
}
