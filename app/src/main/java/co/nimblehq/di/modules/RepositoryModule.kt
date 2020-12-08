package co.nimblehq.di.modules

import co.nimblehq.data.service.ApiService
import co.nimblehq.domain.repository.ApiRepository
import co.nimblehq.domain.repository.ApiRepositoryImpl
import co.nimblehq.domain.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun provideApiRepository(apiService: ApiService, scheduler: SchedulerProvider): ApiRepository =
        ApiRepositoryImpl(apiService, scheduler)
}
