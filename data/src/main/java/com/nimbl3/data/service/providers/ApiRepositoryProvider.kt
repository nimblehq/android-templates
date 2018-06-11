package com.nimbl3.data.service.providers

import com.google.gson.Gson
import com.nimbl3.data.lib.schedulers.SchedulersProvider
import com.nimbl3.data.service.ApiRepository
import com.nimbl3.data.service.ApiRepositoryImpl
import com.nimbl3.data.service.ApiService

class ApiRepositoryProvider {
    companion object {
        fun getApiRepository(apiService: ApiService,
                             schedulers: SchedulersProvider,
                             gson: Gson): ApiRepository {
            return ApiRepositoryImpl(apiService, schedulers, gson)
        }
    }
}