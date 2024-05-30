package co.nimblehq.template.compose.di.modules

import co.nimblehq.template.compose.data.remote.providers.*
import co.nimblehq.template.compose.data.remote.services.ApiService
import co.nimblehq.template.compose.data.remote.services.AuthorizedApiService
import co.nimblehq.template.compose.di.Authorized
import co.nimblehq.template.compose.di.Unauthorized
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.Properties

private const val API_CONFIG_PROPERTIES = "api-config.properties"

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun provideBaseApiUrl(apiConfigProperties: Properties): String =
        apiConfigProperties.getProperty("BASE_API_URL")

    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): Converter.Factory =
        ConverterFactoryProvider.getMoshiConverterFactory(moshi)

    @Unauthorized
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        @Unauthorized okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit = RetrofitProvider
        .getRetrofitBuilder(baseUrl, okHttpClient, converterFactory)
        .build()

    @Authorized
    @Provides
    fun provideAuthorizedRetrofit(
        baseUrl: String,
        @Authorized okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit = RetrofitProvider
        .getRetrofitBuilder(baseUrl, okHttpClient, converterFactory)
        .build()

    @Provides
    fun provideApiService(@Unauthorized retrofit: Retrofit): ApiService =
        ApiServiceProvider.getApiService(retrofit)

    @Provides
    fun provideAuthorizedApiService(@Authorized retrofit: Retrofit): AuthorizedApiService =
        ApiServiceProvider.getAuthorizedService(retrofit)

    @Provides
    fun loadApiConfigProperties(): Properties {
        val properties = Properties()
        val inputStream = this.javaClass.classLoader?.getResourceAsStream(API_CONFIG_PROPERTIES)
            ?: throw IllegalArgumentException(
                "$API_CONFIG_PROPERTIES file not found. " +
                        "Please add $API_CONFIG_PROPERTIES in the :app module /resources folder"
            )

        properties.load(inputStream)

        return properties
    }
}
