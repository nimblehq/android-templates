package co.nimblehq.rxjava.domain.test

import co.nimblehq.rxjava.data.service.response.*
import co.nimblehq.rxjava.domain.data.Data
import co.nimblehq.rxjava.domain.data.toDataList

object MockUtil {

    val exampleData: ExampleResponse
        get() = ExampleResponse(
            ExampleDataResponse(
                listOf(
                    ExampleChildrenResponse(
                        ExampleChildrenDataResponse(
                            author = "author1",
                            title = "title1",
                            thumbnail = "thumbnail"
                        )
                    ),
                    ExampleChildrenResponse(
                        ExampleChildrenDataResponse(
                            author = "author2",
                            title = "title2",
                            thumbnail = "thumbnail"
                        )
                    ),
                    ExampleChildrenResponse(
                        ExampleChildrenDataResponse(
                            author = "author3",
                            title = "title3",
                            thumbnail = "thumbnail"
                        )
                    )
                )
            )
        )

    val dataList: List<Data> = exampleData.toDataList()
}
