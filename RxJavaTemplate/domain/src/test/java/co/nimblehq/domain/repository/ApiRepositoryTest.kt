package co.nimblehq.domain.repository

import co.nimblehq.data.service.ApiService
import co.nimblehq.domain.test.MockUtil
import io.reactivex.Single
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.mock
import org.junit.Test
import retrofit2.Response

class ApiRepositoryTest {

    @Test
    fun `When execute exampleData request successfully, it returns success response`() {
        val mockService = mock<ApiService>()
        When calling mockService.getExampleData() itReturns
            Single.just(Response.success(MockUtil.exampleData))
        val repository = ApiRepositoryImpl(mockService)

        val testSubscriber = repository.exampleData().test()
        testSubscriber
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `When execute exampleData request failed, it returns wrapped error`() {
        val mockService = mock<ApiService>()
        When calling mockService.getExampleData() itReturns Single.error(Throwable())
        val repository = ApiRepositoryImpl(mockService)

        val testSubscriber = repository.exampleData().test()
        testSubscriber
            .assertError(Throwable::class.java)
            .assertValueCount(0)
    }
}
