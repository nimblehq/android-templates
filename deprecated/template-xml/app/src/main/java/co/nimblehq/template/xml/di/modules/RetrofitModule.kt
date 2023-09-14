package co.nimblehq.template.xml.di.modules

import co.nimblehq.template.xml.BuildConfig
import co.nimblehq.template.xml.data.service.ApiService
import co.nimblehq.template.xml.data.service.providers.ApiServiceProvider
import co.nimblehq.template.xml.data.service.providers.ConverterFactoryProvider
import co.nimblehq.template.xml.data.service.providers.RetrofitProvider
import com.squareup.moshi.Moshi
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
    fun provideBaseApiUrl() = BuildConfig.BASE_API_URL

    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): Converter.Factory =
        ConverterFactoryProvider.getMoshiConverterFactory(moshi)

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit = RetrofitProvider
        .getRetrofitBuilder(baseUrl, okHttpClient, converterFactory)
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        ApiServiceProvider.getApiService(retrofit)
}
