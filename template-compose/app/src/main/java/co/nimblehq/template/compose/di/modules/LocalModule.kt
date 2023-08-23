package co.nimblehq.template.compose.di.modules

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import co.nimblehq.template.compose.data.repository.AppPreferencesRepositoryImpl
import co.nimblehq.template.compose.data.storage.EncryptedSharedPreferences
import co.nimblehq.template.compose.domain.repository.AppPreferencesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val APP_PREFERENCES = "app_preferences"

val localModule = module {
    singleOf(::AppPreferencesRepositoryImpl) bind AppPreferencesRepository::class
    single {
        PreferenceDataStoreFactory.create(
            produceFile = { androidContext().preferencesDataStoreFile(APP_PREFERENCES) }
        )
    }
    single {
        EncryptedSharedPreferences(androidContext())
    }
}
