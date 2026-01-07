package co.nimblehq.template.compose.di.modules

import co.nimblehq.template.compose.data.remote.services.ApiService
import co.nimblehq.template.compose.data.repositories.RepositoryImpl
import co.nimblehq.template.compose.domain.repositories.Repository
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
