package com.nimbl3.data.service

import io.reactivex.Flowable
import com.nimbl3.data.service.response.ExampleResponse

interface ApiRepository {
    fun getExampleData(): Flowable<ExampleResponse>
}
