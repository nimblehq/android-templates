package co.nimblehq.template.compose.di.modules

import co.nimblehq.template.compose.util.DispatchersProvider
import co.nimblehq.template.compose.util.DispatchersProviderImpl
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
