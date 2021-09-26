package co.nimblehq.coroutine.di.modules

import co.nimblehq.coroutine.dispatcher.DispatchersProvider
import co.nimblehq.coroutine.dispatcher.DispatchersProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatchersProviderImpl()
    }
}
