package co.nimblehq.data.service

import io.reactivex.Flowable
import co.nimblehq.data.service.response.ExampleResponse

interface ApiRepository {
    fun getExampleData(): Flowable<ExampleResponse>
}
