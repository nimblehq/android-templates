package co.nimblehq.rxjava.data.service.providers

import co.nimblehq.rxjava.data.service.ApiService
import retrofit2.Retrofit

class ApiServiceProvider {
    companion object {
        fun getApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }
}
