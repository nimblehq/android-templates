package co.nimblehq.template.xml.data.service.providers

import co.nimblehq.template.xml.data.service.ApiService
import co.nimblehq.template.xml.data.service.AuthService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    fun getAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}
