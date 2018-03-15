package template.nimbl3.rest.repository

import io.reactivex.Flowable
import template.nimbl3.rest.response.ExampleResponse

interface ApiRepository {
    fun getExampleData(): Flowable<ExampleResponse>
}