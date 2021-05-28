package co.nimblehq.rxjava.di.modules

import co.nimblehq.rxjava.domain.repository.ApiRepository
import co.nimblehq.rxjava.repository.TestApiRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class TestRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTestApiRepository(repository: TestApiRepositoryImpl): ApiRepository
}
