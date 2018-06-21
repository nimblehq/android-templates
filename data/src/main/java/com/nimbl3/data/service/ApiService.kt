
package com.nimbl3.data.service

import io.reactivex.Flowable
import retrofit2.http.GET
import com.nimbl3.data.service.response.ExampleResponse

interface ApiService {

    @GET("/top.json?limit=20")
    fun getExampleData(): Flowable<ExampleResponse>
}
