package co.nimblehq.data.service

import co.nimblehq.data.service.providers.*
import co.nimblehq.domain.repository.ApiRepository
import co.nimblehq.domain.repository.ApiRepositoryImpl
import co.nimblehq.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.domain.schedulers.SchedulerProvider
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit

@Suppress("IllegalIdentifier")
class ApiServiceTest {

    @Test
    fun `API Service components should be initialize-able independently`() {
        val httpClient = OkHttpClient.Builder().build()
        val moshi = MoshiBuilderProvider.moshiBuilder.build()
        val converterFactory = ConverterFactoryProvider.getMoshiConverterFactory(moshi)
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
