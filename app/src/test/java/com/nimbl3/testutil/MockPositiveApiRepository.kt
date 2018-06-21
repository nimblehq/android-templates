package com.nimbl3.testutil

import com.nimbl3.data.service.ApiRepository
import com.nimbl3.data.service.response.ExampleChildrenDataResponse
import com.nimbl3.data.service.response.ExampleChildrenResponse
import com.nimbl3.data.service.response.ExampleDataResponse
import com.nimbl3.data.service.response.ExampleResponse
import io.reactivex.Flowable

object MockPositiveApiRepository : ApiRepository {
    override fun getExampleData(): Flowable<ExampleResponse> {
        val response1 = ExampleChildrenResponse(ExampleChildrenDataResponse("author1", "title1"))
        val response2 = ExampleChildrenResponse(ExampleChildrenDataResponse("author2", "title2"))
        val response3 = ExampleChildrenResponse(ExampleChildrenDataResponse("author3", "title3"))
        return Flowable.just(ExampleResponse(
            ExampleDataResponse(listOf(
                response1, response2, response3,
                response1, response2, response3,
                response1, response2, response3,
                response1, response2, response3,
                response1, response2, response3,
                response1, response2, response3,
                response1, response2))))
    }
}