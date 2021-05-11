package co.nimblehq.coroutine.di.modules

import co.nimblehq.coroutine.data.service.ApiService
import co.nimblehq.coroutine.domain.repository.UserRepository
import co.nimblehq.coroutine.domain.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun provideRepository(apiService: ApiService): UserRepository = UserRepositoryImpl(apiService)
}
