package template.nimbl3.services

import io.reactivex.Flowable
import retrofit2.http.GET
import template.nimbl3.services.response.ExampleResponse

interface ApiService {

    @GET("/top.json?limit=10")
    fun getExampleData(): Flowable<ExampleResponse>
}
