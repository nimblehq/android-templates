package co.nimblehq.template.compose.data.remote.providers

import co.nimblehq.template.compose.data.remote.services.ApiService
import co.nimblehq.template.compose.data.remote.services.AuthorizedApiService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    fun getAuthorizedService(retrofit: Retrofit): AuthorizedApiService {
        return retrofit.create(AuthorizedApiService::class.java)
    }
}
