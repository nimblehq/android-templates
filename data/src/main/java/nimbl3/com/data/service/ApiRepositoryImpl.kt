package nimbl3.com.data.service

import com.google.gson.Gson
import io.reactivex.Flowable
import nimbl3.com.data.lib.schedulers.SchedulersProvider
import nimbl3.com.data.service.response.ExampleResponse

class ApiRepositoryImpl(private val apiService: ApiService,
                        private val schedulers: SchedulersProvider,
                        private val gson: Gson) : ApiRepository {

    override fun getExampleData(): Flowable<ExampleResponse> {
        return apiService
            .getExampleData()
            .subscribeOn(schedulers.io())
    }
}
