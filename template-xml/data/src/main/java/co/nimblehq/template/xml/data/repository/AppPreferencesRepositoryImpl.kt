package co.nimblehq.template.xml.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import co.nimblehq.template.xml.domain.repository.AppPreferencesRepository
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

class AppPreferencesRepositoryImpl @Inject constructor(
    private val appPreferencesDataStore: DataStore<Preferences>
) : AppPreferencesRepository {

    private object PreferencesKeys {
        val APP_PREFERENCES = booleanPreferencesKey("APP_PREFERENCES")
    }

    override fun getAppPreferences(): Flow<Boolean> = appPreferencesDataStore.data
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
            preferences[PreferencesKeys.APP_PREFERENCES] ?: true
        }

    override suspend fun updateAppPreferences(appPreferencesValue: Boolean) {
        appPreferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.APP_PREFERENCES] = appPreferencesValue
        }
    }
}
