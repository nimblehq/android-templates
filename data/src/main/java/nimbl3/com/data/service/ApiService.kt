package nimbl3.com.data.service

import io.reactivex.Flowable
import retrofit2.http.GET
import nimbl3.com.data.service.response.ExampleResponse

interface ApiService {

    @GET("/top.json?limit=10")
    fun getExampleData(): Flowable<ExampleResponse>
}
