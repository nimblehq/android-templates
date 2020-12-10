package co.nimblehq.domain.repository

import co.nimblehq.data.service.ApiService
import co.nimblehq.data.service.response.ExampleResponse
import co.nimblehq.domain.schedulers.BaseSchedulerProvider
import io.reactivex.Flowable
import javax.inject.Inject

interface ApiRepository {
    fun getExampleData(): Flowable<ExampleResponse>
}

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val schedulers: BaseSchedulerProvider
) : ApiRepository {

    override fun getExampleData(): Flowable<ExampleResponse> {
        return apiService
            .getExampleData()
            .subscribeOn(schedulers.io())
    }
}
