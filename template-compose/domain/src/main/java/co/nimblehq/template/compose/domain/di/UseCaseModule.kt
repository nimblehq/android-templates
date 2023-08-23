package co.nimblehq.template.compose.domain.di

import co.nimblehq.template.compose.domain.usecase.UseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::UseCase) bind UseCase::class
}
