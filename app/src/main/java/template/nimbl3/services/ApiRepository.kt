package template.nimbl3.services

import io.reactivex.Flowable
import template.nimbl3.services.response.ExampleResponse

interface ApiRepository {
    fun getExampleData(): Flowable<ExampleResponse>
}
