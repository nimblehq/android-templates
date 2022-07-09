package co.nimblehq.coroutine.di.modules

import co.nimblehq.coroutine.data.repository.RepositoryImpl
import co.nimblehq.coroutine.data.service.ApiService
import co.nimblehq.coroutine.domain.repository.Repository
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
