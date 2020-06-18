package co.nimblehq.data.service.response

data class ExampleResponse(val data: ExampleDataResponse)

data class ExampleDataResponse(
    val children: List<ExampleChildrenResponse>
)

data class ExampleChildrenResponse(val data: ExampleChildrenDataResponse)

data class ExampleChildrenDataResponse(
    val author: String,
    val title: String
)
