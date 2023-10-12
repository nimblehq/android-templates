package co.nimblehq.template.compose.data.di.modules

import co.nimblehq.template.compose.data.repositories.RepositoryImpl
import co.nimblehq.template.compose.domain.repositories.Repository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::RepositoryImpl) bind Repository::class
}
