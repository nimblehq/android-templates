package co.nimblehq.template.compose.di.modules

import co.nimblehq.template.compose.BuildConfig
import co.nimblehq.template.compose.data.di.modules.BASE_API_URL
import com.chuckerteam.chucker.api.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

private const val READ_TIME_OUT = 30L

val httpClientModule = module {
    single(named(BASE_API_URL)) {
        BuildConfig.BASE_API_URL
    }
    single {
        val chuckerCollector = ChuckerCollector(
            context = androidContext(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        ChuckerInterceptor.Builder(androidContext())
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
}
