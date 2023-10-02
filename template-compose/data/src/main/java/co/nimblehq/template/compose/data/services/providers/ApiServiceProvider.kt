package co.nimblehq.template.compose.data.services.providers

import co.nimblehq.template.compose.data.services.ApiService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
