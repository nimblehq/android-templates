package co.nimblehq.template.compose.data.di.modules

import co.nimblehq.template.compose.data.service.providers.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BASE_API_URL = "BASE_API_URL"

val remoteModule = module {
    single {
        MoshiBuilderProvider.moshiBuilder.build()
    }
    single {
        ConverterFactoryProvider.getMoshiConverterFactory(get())
    }
    single {
        RetrofitProvider
            .getRetrofitBuilder(get(named(BASE_API_URL)), get(), get())
            .build()
    }
    single {
        ApiServiceProvider.getApiService(get())
    }
}
