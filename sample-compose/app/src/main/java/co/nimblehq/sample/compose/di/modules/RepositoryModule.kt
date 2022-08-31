package co.nimblehq.sample.compose.di.modules

import co.nimblehq.sample.compose.data.repository.RepositoryImpl
import co.nimblehq.sample.compose.data.service.ApiService
import co.nimblehq.sample.compose.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideRepository(apiService: ApiService): Repository = RepositoryImpl(apiService)
}
