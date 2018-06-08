package com.nimbl3.data.service.response

class ExampleResponse(val data: ExampleDataResponse)

class ExampleDataResponse(
    val children: List<ExampleChildrenResponse>
)

class ExampleChildrenResponse(val data: ExampleChildrenDataResponse)

class ExampleChildrenDataResponse(
    val author: String,
    val title: String
)
