package co.nimblehq.template.compose.di.modules

import co.nimblehq.template.compose.data.remote.services.ApiService
import co.nimblehq.template.compose.data.repositories.TokenRepositoryImpl
import co.nimblehq.template.compose.data.util.DispatchersProvider
import co.nimblehq.template.compose.data.util.DispatchersProviderImpl
import co.nimblehq.template.compose.domain.repositories.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Properties

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatchersProviderImpl()
    }

    @Provides
    fun provideTokenRepository(
        apiService: ApiService,
        apiConfigProperties: Properties,
    ): TokenRepository = TokenRepositoryImpl(
        apiService = apiService,
        apiConfigProperties = apiConfigProperties,
    )
}
