package com.nimbl3.data.service.providers

import com.nimbl3.data.service.ApiService
import retrofit2.Retrofit

class ApiServiceProvider {
    companion object {
        fun getApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }
}