package co.nimblehq.rxjava.domain.test

import co.nimblehq.rxjava.data.service.response.ExampleChildrenDataResponse
import co.nimblehq.rxjava.data.service.response.ExampleChildrenResponse
import co.nimblehq.rxjava.data.service.response.ExampleDataResponse
import co.nimblehq.rxjava.data.service.response.ExampleResponse
import co.nimblehq.rxjava.domain.data.Data
import co.nimblehq.rxjava.domain.data.toDataList

object MockUtil {

    val exampleData: ExampleResponse
        get() = ExampleResponse(
            ExampleDataResponse(
                listOf(
                    ExampleChildrenResponse(
                        ExampleChildrenDataResponse(
                            title = "title1",
                            author = "author1",
                            thumbnail = "thumbnail",
                            url = "https://www.google.com"
                        )
                    ),
                    ExampleChildrenResponse(
                        ExampleChildrenDataResponse(
                            title = "title2",
                            author = "author2",
                            thumbnail = "thumbnail",
                            url = "https://www.google.com"
                        )
                    ),
                    ExampleChildrenResponse(
                        ExampleChildrenDataResponse(
                            title = "title3",
                            author = "author3",
                            thumbnail = "thumbnail",
                            url = "https://www.google.com"
                        )
                    )
                )
            )
        )

    val dataList: List<Data> = exampleData.toDataList()
}
