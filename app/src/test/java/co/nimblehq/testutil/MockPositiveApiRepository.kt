package co.nimblehq.testutil

import co.nimblehq.data.service.response.*
import co.nimblehq.domain.repository.ApiRepository
import io.reactivex.Flowable

object MockPositiveApiRepository : ApiRepository {
    override fun getExampleData(): Flowable<ExampleResponse> {
        val response1 = ExampleChildrenResponse(ExampleChildrenDataResponse("author1", "title1"))
        val response2 = ExampleChildrenResponse(ExampleChildrenDataResponse("author2", "title2"))
        val response3 = ExampleChildrenResponse(ExampleChildrenDataResponse("author3", "title3"))
        return Flowable.just(ExampleResponse(ExampleDataResponse(listOf(response1, response2, response3))))
    }
}
