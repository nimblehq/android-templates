package co.nimblehq.data.service

import com.google.gson.Gson
import co.nimblehq.data.lib.schedulers.SchedulersProvider
import co.nimblehq.data.lib.schedulers.SchedulersProviderImpl
import co.nimblehq.data.service.providers.ApiRepositoryProvider
import co.nimblehq.data.service.providers.ApiServiceProvider
import co.nimblehq.data.service.providers.ConverterFactoryProvider
import co.nimblehq.data.service.providers.RetrofitProvider
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit

@Suppress("IllegalIdentifier")
class ApiServiceTest {

    @Test
    fun `API Service components should be initialize-able independently`() {
        val httpClient = OkHttpClient.Builder().build()
        val gson = Gson()
        val converterFactory = ConverterFactoryProvider.getConverterFactoryProvider(gson)
        val retrofitBuilder = RetrofitProvider.getRetrofitBuilder(converterFactory, httpClient)
        val appRetrofit: Retrofit = retrofitBuilder.build()


        val schedulers: SchedulersProvider = SchedulersProviderImpl()
        Assert.assertNotNull("should provide Retrofit", appRetrofit)

        val apiService: ApiService = ApiServiceProvider.getApiService(appRetrofit)
        Assert.assertNotNull("should provide ApiService", apiService)

        val apiRepository: ApiRepository = ApiRepositoryProvider
            .getApiRepository(apiService, schedulers, gson)
        Assert.assertNotNull("should provide ApiRepository", apiRepository)
    }
}
