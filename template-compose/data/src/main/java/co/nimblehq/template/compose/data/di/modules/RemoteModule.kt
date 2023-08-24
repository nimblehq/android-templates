package co.nimblehq.template.compose.data.di.modules

import co.nimblehq.template.compose.data.BuildConfig
import co.nimblehq.template.compose.data.service.providers.*
import com.chuckerteam.chucker.api.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

const val BASE_API_URL = "BASE_API_URL"
private const val READ_TIME_OUT = 30L

val remoteModule = module {
    single {
        val chuckerCollector = ChuckerCollector(
            context = get(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        ChuckerInterceptor.Builder(get())
            .collector(chuckerCollector)
            .alwaysReadResponseBody(true)
            .build()
    }
    single {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                addInterceptor(get<ChuckerInterceptor>())
                readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            }
        }.build()
    }
    single {
        MoshiBuilderProvider.moshiBuilder.build()
    }
    single {
        ConverterFactoryProvider.getMoshiConverterFactory(moshi = get())
    }
    single {
        RetrofitProvider
            .getRetrofitBuilder(
                baseUrl = get(named(BASE_API_URL)),
                okHttpClient = get(),
                converterFactory = get()
            )
            .build()
    }
    single {
        ApiServiceProvider.getApiService(get())
    }
}
