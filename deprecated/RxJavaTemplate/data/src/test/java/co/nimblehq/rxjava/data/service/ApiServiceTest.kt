package co.nimblehq.rxjava.data.service

import co.nimblehq.rxjava.data.service.providers.ApiServiceProvider
import co.nimblehq.rxjava.data.service.providers.ConverterFactoryProvider
import co.nimblehq.rxjava.data.service.providers.MoshiBuilderProvider
import co.nimblehq.rxjava.data.service.providers.RetrofitProvider
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit

class ApiServiceTest {

    @Test
    fun `API Service components should be initialize-able independently`() {
        val httpClient = OkHttpClient.Builder().build()
        val moshi = MoshiBuilderProvider.moshiBuilder.build()
        val converterFactory = ConverterFactoryProvider.getMoshiConverterFactory(moshi)
        val retrofitBuilder = RetrofitProvider.getRetrofitBuilder(converterFactory, httpClient)
        val appRetrofit: Retrofit = retrofitBuilder.build()


        Assert.assertNotNull("should provide Retrofit", appRetrofit)

        val apiService: ApiService = ApiServiceProvider.getApiService(appRetrofit)
        Assert.assertNotNull("should provide ApiService", apiService)
    }
}
