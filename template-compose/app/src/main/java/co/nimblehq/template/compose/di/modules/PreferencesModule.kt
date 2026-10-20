package co.nimblehq.template.compose.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import co.nimblehq.template.compose.data.repositories.AppPreferencesRepositoryImpl
import co.nimblehq.template.compose.data.repositories.AuthPreferenceRepositoryImpl
import co.nimblehq.template.compose.domain.repositories.AppPreferencesRepository
import co.nimblehq.template.compose.domain.repositories.AuthPreferenceRepository
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val APP_PREFERENCES = "app_preferences"

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun bindAppPreferencesRepository(
        appPreferencesRepositoryImpl: AppPreferencesRepositoryImpl
    ): AppPreferencesRepository

    @Binds
    abstract fun bindAuthPreferencesRepository(
        authPreferenceRepositoryImpl: AuthPreferenceRepositoryImpl
    ): AuthPreferenceRepository

    companion object {
        @Singleton
        @Provides
        fun provideAppPreferencesDataStore(
            @ApplicationContext appContext: Context
        ): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                produceFile = { appContext.preferencesDataStoreFile(APP_PREFERENCES) }
            )
        }
    }
}
