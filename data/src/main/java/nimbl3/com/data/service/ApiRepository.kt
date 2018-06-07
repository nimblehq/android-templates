package nimbl3.com.data.service

import io.reactivex.Flowable
import nimbl3.com.data.service.response.ExampleResponse

interface ApiRepository {
    fun getExampleData(): Flowable<ExampleResponse>
}
