package co.nimblehq.coroutine.di.modules

import co.nimblehq.coroutine.BuildConfig
import co.nimblehq.coroutine.data.service.ApiService
import co.nimblehq.coroutine.data.service.providers.ApiServiceProvider
import co.nimblehq.coroutine.data.service.providers.ConverterFactoryProvider
import co.nimblehq.coroutine.data.service.providers.RetrofitProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    fun provideGsonConverterFactory(): Converter.Factory =
        ConverterFactoryProvider.getGsonConverterFactory()

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit = RetrofitProvider
        .getRetrofitBuilder(baseUrl, okHttpClient, converterFactory)
        .build()

    @Provides
    fun provideApi(retrofit: Retrofit): ApiService = ApiServiceProvider.getApiService(retrofit)
}
