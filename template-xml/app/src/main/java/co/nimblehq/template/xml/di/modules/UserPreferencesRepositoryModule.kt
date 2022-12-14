package co.nimblehq.template.xml.di.modules

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import co.nimblehq.template.xml.data.repository.UserPreferencesRepositoryImpl
import co.nimblehq.template.xml.domain.repository.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UserPreferencesRepositoryModule {

    @Provides
    fun provideUserPreferencesRepository(dataStore: DataStore<Preferences>): UserPreferencesRepository =
        UserPreferencesRepositoryImpl(dataStore)
}
