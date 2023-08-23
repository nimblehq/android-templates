package co.nimblehq.template.compose.di.modules

import co.nimblehq.template.compose.util.DispatchersProvider
import co.nimblehq.template.compose.util.DispatchersProviderImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::DispatchersProviderImpl) bind DispatchersProvider::class
}
