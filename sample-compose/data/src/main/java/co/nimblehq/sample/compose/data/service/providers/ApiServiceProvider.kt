package co.nimblehq.sample.compose.data.service.providers

import co.nimblehq.sample.compose.data.service.ApiService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
