package co.nimblehq.template.compose.domain.di

import co.nimblehq.template.compose.domain.usecase.UseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::UseCase) bind UseCase::class
}
