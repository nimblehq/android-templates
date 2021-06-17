package co.nimblehq.rxjava.di.modules

import android.content.Context
import co.nimblehq.rxjava.storage.BaseSecuredStorage
import co.nimblehq.rxjava.storage.BaseSecuredStorageImpl
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
        fun provideBaseSecuredStorage(@ApplicationContext context: Context): BaseSecuredStorage =
            BaseSecuredStorageImpl(context)
    }
}
