package co.nimblehq.coroutine.di.modules

import co.nimblehq.coroutine.data.service.ApiService
import co.nimblehq.coroutine.repository.UserRepository
import co.nimblehq.coroutine.repositoryimpl.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideUserRepository(apiService: ApiService): UserRepository =
        UserRepositoryImpl(apiService)
}
