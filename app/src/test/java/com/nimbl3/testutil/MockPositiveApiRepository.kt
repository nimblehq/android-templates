package com.nimbl3.testutil

import co.nimblehq.data.service.ApiRepository
import co.nimblehq.data.service.response.ExampleChildrenDataResponse
import co.nimblehq.data.service.response.ExampleChildrenResponse
import co.nimblehq.data.service.response.ExampleDataResponse
import co.nimblehq.data.service.response.ExampleResponse
import io.reactivex.Flowable

object MockPositiveApiRepository : ApiRepository {
    override fun getExampleData(): Flowable<ExampleResponse> {
        val response1 = ExampleChildrenResponse(ExampleChildrenDataResponse("author1", "title1"))
        val response2 = ExampleChildrenResponse(ExampleChildrenDataResponse("author2", "title2"))
        val response3 = ExampleChildrenResponse(ExampleChildrenDataResponse("author3", "title3"))
        return Flowable.just(ExampleResponse(ExampleDataResponse(listOf(response1, response2, response3))))
    }
}
