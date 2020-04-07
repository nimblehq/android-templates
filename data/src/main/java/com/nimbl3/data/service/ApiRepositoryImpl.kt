package com.nimbl3.data.service

import com.google.gson.Gson
import com.nimbl3.data.lib.schedulers.SchedulersProvider
import com.nimbl3.data.service.response.ExampleResponse
import io.reactivex.Flowable

class ApiRepositoryImpl(
    private val apiService: ApiService,
    private val schedulers: SchedulersProvider,
    private val gson: Gson
) : ApiRepository {

    override fun getExampleData(): Flowable<ExampleResponse> {
        return apiService
            .getExampleData()
            .subscribeOn(schedulers.io())
    }
}
