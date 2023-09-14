package co.nimblehq.sample.xml.di.modules

import co.nimblehq.sample.xml.data.repository.RepositoryImpl
import co.nimblehq.sample.xml.data.service.ApiService
import co.nimblehq.sample.xml.domain.repository.Repository
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
