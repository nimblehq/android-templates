package template.nimbl3.rest.api

import io.reactivex.Flowable
import retrofit2.http.GET
import template.nimbl3.rest.response.ExampleResponse

interface ApiService {

    @GET("/top.json?limit=10")
    fun getExampleData(): Flowable<ExampleResponse>
}
