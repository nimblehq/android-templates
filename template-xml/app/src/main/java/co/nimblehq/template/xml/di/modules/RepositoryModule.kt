package co.nimblehq.template.xml.di.modules

import co.nimblehq.template.xml.data.repository.RepositoryImpl
import co.nimblehq.template.xml.data.service.ApiService
import co.nimblehq.template.xml.domain.repository.Repository
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
