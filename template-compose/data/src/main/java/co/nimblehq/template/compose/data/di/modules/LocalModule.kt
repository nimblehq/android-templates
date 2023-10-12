package co.nimblehq.template.compose.data.di.modules

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import co.nimblehq.template.compose.data.repositories.AppPreferencesRepositoryImpl
import co.nimblehq.template.compose.data.storages.EncryptedSharedPreferences
import co.nimblehq.template.compose.domain.repositories.AppPreferencesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val APP_PREFERENCES = "app_preferences"

val localModule = module {
    singleOf(::AppPreferencesRepositoryImpl) bind AppPreferencesRepository::class
    single {
        PreferenceDataStoreFactory.create(
            produceFile = { get<Context>().preferencesDataStoreFile(APP_PREFERENCES) }
        )
    }
    single {
        EncryptedSharedPreferences(get())
    }
}
