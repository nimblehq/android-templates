package com.nimbl3.di.modules

import com.google.gson.Gson
import com.nimbl3.data.lib.schedulers.SchedulersProvider
import com.nimbl3.data.service.ApiRepository
import com.nimbl3.data.service.ApiRepositoryImpl
import com.nimbl3.data.service.ApiService
import com.nimbl3.data.service.interceptor.AppRequestInterceptor
import com.nimbl3.data.service.providers.ApiServiceProvider
import com.nimbl3.data.service.providers.ConverterFactoryProvider
import com.nimbl3.data.service.providers.RetrofitProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton


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