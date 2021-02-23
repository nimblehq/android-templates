package co.nimblehq.domain.repository

import co.nimblehq.data.service.ApiService
import co.nimblehq.domain.data.Data
import co.nimblehq.domain.data.toData
import co.nimblehq.domain.transform
import io.reactivex.Single
import javax.inject.Inject

interface ApiRepository {

    fun exampleData(): Single<Data>
}

class ApiRepositoryImpl @Inject constructor(
    private val service: ApiService
) : ApiRepository {

    override fun exampleData(): Single<Data> {
        return service
            .getExampleData()
            .transform()
            .map { it.toData() }
    }
}
