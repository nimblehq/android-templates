package co.nimblehq.template.compose.di.modules

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import co.nimblehq.template.compose.data.repository.PreferencesRepositoryImpl
import co.nimblehq.template.compose.domain.repository.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class PreferencesRepositoryModule {

    @Provides
    fun providePreferencesRepository(dataStore: DataStore<Preferences>): PreferencesRepository =
        PreferencesRepositoryImpl(dataStore)
}
