package co.nimblehq.rxjava.di.modules

import android.content.Context
import co.nimblehq.rxjava.storage.BaseEncryptedSharedPreferences
import co.nimblehq.rxjava.storage.BaseEncryptedSharedPreferencesImpl
import co.nimblehq.rxjava.storage.BaseSharedPreferences
import co.nimblehq.rxjava.storage.BaseSharedPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class StorageModule {

    companion object {

        @Provides
        @Singleton
        fun provideBaseSecuredLocalStorage(@ApplicationContext context: Context): BaseEncryptedSharedPreferences =
            BaseEncryptedSharedPreferencesImpl(context)

        @Provides
        @Singleton
        fun provideBaseLocalStorage(@ApplicationContext context: Context): BaseSharedPreferences =
            BaseSharedPreferencesImpl(context)
    }
}
