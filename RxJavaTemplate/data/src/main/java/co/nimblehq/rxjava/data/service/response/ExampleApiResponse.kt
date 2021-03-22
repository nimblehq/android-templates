package co.nimblehq.rxjava.data.service.response

data class ExampleResponse(val data: ExampleDataResponse)

data class ExampleDataResponse(
    val children: List<ExampleChildrenResponse>
)

data class ExampleChildrenResponse(val data: ExampleChildrenDataResponse)

data class ExampleChildrenDataResponse(
    val title: String,
    val author: String,
    val thumbnail: String
)
