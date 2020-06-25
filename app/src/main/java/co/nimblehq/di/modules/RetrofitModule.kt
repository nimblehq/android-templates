package co.nimblehq.di.modules

import co.nimblehq.data.service.ApiService
import co.nimblehq.data.service.interceptor.AppRequestInterceptor
import co.nimblehq.data.service.providers.*
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@InstallIn(ApplicationComponent::class)
@Module
class RetrofitModule {

    @Provides
    fun provideApiRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return RetrofitProvider
            .getRetrofitBuilder(converterFactory, okHttpClient)
            .build()
    }

    @Provides
    fun provideConverterFactory(gson: Gson): Converter.Factory {
        return ConverterFactoryProvider.getConverterFactoryProvider(gson)
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        ApiServiceProvider.getApiService(retrofit)

    @Provides
    fun provideAppRequestInterceptor(): AppRequestInterceptor = AppRequestInterceptor()
}
