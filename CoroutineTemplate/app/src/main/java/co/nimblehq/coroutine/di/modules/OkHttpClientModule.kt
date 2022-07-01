package co.nimblehq.coroutine.di.modules

import co.nimblehq.coroutine.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

private const val READ_TIME_OUT = 30L

@Module
@InstallIn(SingletonComponent::class)
class OkHttpClientModule {

    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        }
    }
        .build()
}
