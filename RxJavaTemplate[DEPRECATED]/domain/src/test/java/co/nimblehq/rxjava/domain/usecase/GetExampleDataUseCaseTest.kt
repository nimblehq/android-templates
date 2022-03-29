package co.nimblehq.rxjava.domain.usecase

import co.nimblehq.rxjava.domain.data.error.DataError
import co.nimblehq.rxjava.domain.repository.ApiRepository
import co.nimblehq.rxjava.domain.schedulers.TrampolineSchedulerProvider
import co.nimblehq.rxjava.domain.test.MockUtil
import io.reactivex.Single
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test

class GetExampleDataUseCaseTest {

    private val mockRepository = mock<ApiRepository>()
    private lateinit var useCase: GetExampleDataUseCase

    @Before
    fun setup() {
        useCase = GetExampleDataUseCase(
            TrampolineSchedulerProvider,
            mockRepository
        )
    }

    @Test
    fun `When execute usecase request successfully, it returns positive result`() {
        val data = MockUtil.dataList
        When calling mockRepository.exampleData() itReturns Single.just(data)

        val testSubscriber = useCase.execute(Unit).test()
        testSubscriber
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue(data)
    }

    @Test
    fun `When execute usecase request failed, it returns wrapped error`() {
        When calling mockRepository.exampleData() itReturns Single.error(Throwable())

        val testSubscriber = useCase.execute(Unit).test()
        testSubscriber
            .assertError { it is DataError.GetDataError }
            .assertValueCount(0)
    }
}
