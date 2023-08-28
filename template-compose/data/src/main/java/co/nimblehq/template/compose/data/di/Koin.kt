package co.nimblehq.template.compose.data.di

import co.nimblehq.template.compose.data.di.modules.*
import co.nimblehq.template.compose.domain.di.useCaseModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
    val dataModules = listOf(localModule, remoteModule, repositoryModule)
    val domainModules = listOf(useCaseModule)
    return startKoin {
        appDeclaration()
        modules(
            dataModules + domainModules
        )
    }
}
