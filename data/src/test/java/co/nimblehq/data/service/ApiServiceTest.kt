package co.nimblehq.data.service

import co.nimblehq.data.service.providers.ApiServiceProvider
import co.nimblehq.data.service.providers.ConverterFactoryProvider
import co.nimblehq.data.service.providers.RetrofitProvider
import co.nimblehq.domain.repository.ApiRepository
import co.nimblehq.domain.repository.ApiRepositoryImpl
import co.nimblehq.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.domain.schedulers.SchedulerProvider
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit

class ApiServiceTest {

    @Test
    fun `API Service components should be initialize-able independently`() {
        val httpClient = OkHttpClient.Builder().build()
        val gson = Gson()
        val converterFactory = ConverterFactoryProvider.getConverterFactoryProvider(gson)
        val retrofitBuilder = RetrofitProvider.getRetrofitBuilder(converterFactory, httpClient)
        val appRetrofit: Retrofit = retrofitBuilder.build()


        val schedulers: BaseSchedulerProvider = SchedulerProvider()
        Assert.assertNotNull("should provide Retrofit", appRetrofit)

        val apiService: ApiService = ApiServiceProvider.getApiService(appRetrofit)
        Assert.assertNotNull("should provide ApiService", apiService)

        val apiRepository: ApiRepository = ApiRepositoryImpl(apiService, schedulers)
        Assert.assertNotNull("should provide ApiRepository", apiRepository)
    }
}
