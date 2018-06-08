package com.nimbl3.di.modules

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import com.nimbl3.data.lib.schedulers.SchedulersProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.nimbl3.BuildConfig
import com.nimbl3.R
import com.nimbl3.data.service.ApiRepository
import com.nimbl3.data.service.ApiRepositoryImpl
import com.nimbl3.data.service.ApiService
import com.nimbl3.data.service.interceptor.AppRequestInterceptor
import javax.inject.Singleton


@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideApiRetrofit(context: Context, gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.api_endpoint_example))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiClientType(apiService: ApiService, scheduler: SchedulersProvider, gson: Gson): ApiRepository {
        return ApiRepositoryImpl(apiService, scheduler, gson)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideOkHttpClient(apiRequestInterceptor: AppRequestInterceptor,
                            httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder().addInterceptor(apiRequestInterceptor)
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(httpLoggingInterceptor)
        }
        return httpClient.build()
    }

    @Provides
    fun provideAppRequestInterceptor(): AppRequestInterceptor = AppRequestInterceptor()

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}