package template.nimbl3.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import template.nimbl3.TemplateApplication
import template.nimbl3.rest.api.ApiService
import template.nimbl3.rest.repository.ApiRepository
import template.nimbl3.rest.repository.ApiRepositoryImpl
import template.nimbl3.rest.service.AppRequestInterceptor
import template.nimbl3.ui.BuildConfig
import template.nimbl3.ui.R
import javax.inject.Singleton

@Module
class AppModule(val appContext: TemplateApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = appContext

    @Provides
    @Singleton
    fun provideApplication(): Application = appContext

    @Provides
    @Singleton
    fun provideApiRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return createRetrofit(gson, okHttpClient)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiClientType(apiService: ApiService, gson: Gson): ApiRepository {
        return ApiRepositoryImpl(apiService, gson)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(apiRequestInterceptor: AppRequestInterceptor,
                            httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder().addInterceptor(apiRequestInterceptor)
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(httpLoggingInterceptor)
        }
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideAppRequestInterceptor(): AppRequestInterceptor = AppRequestInterceptor()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    private fun createRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(appContext.getString(R.string.api_endpoint_example))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }
}