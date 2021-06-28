package co.nimblehq.rxjava.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import co.nimblehq.rxjava.storage.BaseSharedPreferences
import co.nimblehq.rxjava.storage.EncryptedSharedPreferences
import co.nimblehq.rxjava.storage.NormalSharedPreferences
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
        fun provideDefaultSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

        @Provides
        @Singleton
        fun provideSecuredLocalStorage(@ApplicationContext context: Context): BaseSharedPreferences =
            EncryptedSharedPreferences(context)

        @Provides
        @Singleton
        fun provideNormalLocalStorage(
            @ApplicationContext context: Context,
            defaultSharedPreferences: SharedPreferences
        ): BaseSharedPreferences =
            NormalSharedPreferences(context, defaultSharedPreferences)
    }
}
