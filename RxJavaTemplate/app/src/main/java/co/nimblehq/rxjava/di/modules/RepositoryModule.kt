package co.nimblehq.rxjava.di.modules

import co.nimblehq.rxjava.data.service.ApiService
import co.nimblehq.rxjava.domain.repository.ApiRepository
import co.nimblehq.rxjava.domain.repository.ApiRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun provideApiRepository(
        apiService: ApiService
    ): ApiRepository = ApiRepositoryImpl(apiService)
}
