package co.nimblehq.sample.xml.di.modules

import co.nimblehq.sample.xml.util.DispatchersProvider
import co.nimblehq.sample.xml.util.DispatchersProviderImpl
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
