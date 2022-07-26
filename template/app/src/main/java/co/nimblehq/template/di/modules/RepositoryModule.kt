package co.nimblehq.template.di.modules

import co.nimblehq.template.data.repository.RepositoryImpl
import co.nimblehq.template.data.service.ApiService
import co.nimblehq.template.domain.repository.Repository
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
