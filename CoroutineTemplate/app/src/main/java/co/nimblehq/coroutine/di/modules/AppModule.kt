package co.nimblehq.coroutine.di.modules

import co.nimblehq.coroutine.util.DispatchersProvider
import co.nimblehq.coroutine.util.DispatchersProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatchersProviderImpl()
    }
}
