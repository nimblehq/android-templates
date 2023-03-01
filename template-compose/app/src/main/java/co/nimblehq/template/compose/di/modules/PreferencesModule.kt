package co.nimblehq.template.compose.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import co.nimblehq.template.compose.data.repository.AppPreferencesRepositoryImpl
import co.nimblehq.template.compose.domain.repository.AppPreferencesRepository
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun bindAppPreferencesRepository(
        pppPreferencesRepositoryImpl: AppPreferencesRepositoryImpl
    ): AppPreferencesRepository

    companion object {
        private val Context.appPreferencesDataStore by preferencesDataStore("app_preferences")

        @Provides
        fun provideAppPreferencesDataStore(
            @ApplicationContext applicationContext: Context
        ): DataStore<Preferences> = applicationContext.appPreferencesDataStore
    }
}
