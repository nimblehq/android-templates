package co.nimblehq.data.service.providers

import com.google.gson.Gson
import co.nimblehq.data.lib.schedulers.SchedulersProvider
import co.nimblehq.data.service.ApiRepository
import co.nimblehq.data.service.ApiRepositoryImpl
import co.nimblehq.data.service.ApiService

class ApiRepositoryProvider {
    companion object {
        fun getApiRepository(apiService: ApiService,
                             schedulers: SchedulersProvider,
                             gson: Gson): ApiRepository {
            return ApiRepositoryImpl(apiService, schedulers, gson)
        }
    }
}
