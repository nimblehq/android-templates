package co.nimblehq.rxjava.repository

import co.nimblehq.rxjava.domain.data.Data
import co.nimblehq.rxjava.domain.repository.ApiRepository
import co.nimblehq.rxjava.domain.test.MockUtil
import io.reactivex.Single
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject

class TestApiRepositoryImpl @Inject constructor() : ApiRepository {

    override fun exampleData(): Single<List<Data>> {
        return Single.just(MockUtil.dataList).delay(500, MILLISECONDS)
    }
}
