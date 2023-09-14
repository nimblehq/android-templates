package co.nimblehq.sample.xml.data.service.providers

import co.nimblehq.sample.xml.data.service.ApiService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
