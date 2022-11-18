package co.nimblehq.template.compose.di.modules

import android.content.Context
import co.nimblehq.template.compose.BuildConfig
import com.chuckerteam.chucker.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.*

private const val READ_TIME_OUT = 30L

@Module
@InstallIn(SingletonComponent::class)
class OkHttpClientModule {

    @Provides
    fun provideOkHttpClient(
        chuckerInterceptor: ChuckerInterceptor
    ) = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            addInterceptor(chuckerInterceptor)
            readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        }
    }.build()

    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context
    ): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .alwaysReadResponseBody(true)
            .build()
    }
}
