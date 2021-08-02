package co.nimblehq.rxjava.di.modules

import co.nimblehq.rxjava.domain.repository.ApiRepository
import co.nimblehq.rxjava.repository.TestApiRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
@Module
abstract class TestRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTestApiRepository(repository: TestApiRepositoryImpl): ApiRepository
}
