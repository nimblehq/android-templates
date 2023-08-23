package co.nimblehq.template.compose.data.di.modules

import co.nimblehq.template.compose.data.repository.RepositoryImpl
import co.nimblehq.template.compose.domain.repository.Repository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::RepositoryImpl) bind Repository::class
}
