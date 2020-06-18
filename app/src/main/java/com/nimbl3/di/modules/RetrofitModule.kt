package com.nimbl3.di.modules

import com.google.gson.Gson
import co.nimblehq.data.lib.schedulers.SchedulersProvider
import co.nimblehq.data.service.ApiRepository
import co.nimblehq.data.service.ApiRepositoryImpl
import co.nimblehq.data.service.ApiService
import co.nimblehq.data.service.interceptor.AppRequestInterceptor
import co.nimblehq.data.service.providers.ApiServiceProvider
import co.nimblehq.data.service.providers.ConverterFactoryProvider
import co.nimblehq.data.service.providers.RetrofitProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RetrofitModule {
    @Provides
    @Singleton
    fun provideApiRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return RetrofitProvider
            .getRetrofitBuilder(converterFactory, okHttpClient)
            .build()
    }

    @Provides
    fun provideConverterFactory(gson: Gson): Converter.Factory {
        return ConverterFactoryProvider.getConverterFactoryProvider(gson)
    }

    @Provides
    @Singleton
    fun provideApiClientType(apiService: ApiService, scheduler: SchedulersProvider, gson: Gson): ApiRepository {
        return ApiRepositoryImpl(apiService, scheduler, gson)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = ApiServiceProvider.getApiService(retrofit)


    @Provides
    fun provideAppRequestInterceptor(): AppRequestInterceptor = AppRequestInterceptor()
}
