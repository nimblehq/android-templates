package co.nimblehq.sample.compose.data.remote.providers

import co.nimblehq.sample.compose.data.remote.services.ApiService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
