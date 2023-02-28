package co.nimblehq.template.compose.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import co.nimblehq.template.compose.data.repository.AppPreferencesRepositoryImpl
import co.nimblehq.template.compose.domain.repository.AppPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.appPreferencesDataStore by preferencesDataStore("app_preferences")

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    @Provides
    @Singleton
    fun provideAppPreferencesDataStore(
        @ApplicationContext applicationContext: Context
    ): DataStore<Preferences> = applicationContext.appPreferencesDataStore

    @Provides
    fun provideAppPreferencesRepository(
        appPreferencesDataStore: DataStore<Preferences>
    ): AppPreferencesRepository = AppPreferencesRepositoryImpl(appPreferencesDataStore)
}
