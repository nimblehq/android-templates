package co.nimblehq.domain.test

import co.nimblehq.data.service.response.ExampleChildrenDataResponse
import co.nimblehq.data.service.response.ExampleChildrenResponse
import co.nimblehq.data.service.response.ExampleDataResponse
import co.nimblehq.data.service.response.ExampleResponse
import co.nimblehq.domain.data.Data
import co.nimblehq.domain.data.toData

object MockUtil {

    val exampleData: ExampleResponse
        get() = ExampleResponse(
            ExampleDataResponse(
                listOf(
                    ExampleChildrenResponse(ExampleChildrenDataResponse("author1", "title1")),
                    ExampleChildrenResponse(ExampleChildrenDataResponse("author2", "title2")),
                    ExampleChildrenResponse(ExampleChildrenDataResponse("author3", "title3"))
                )
            )
        )

    val data: Data = exampleData.toData()
}
