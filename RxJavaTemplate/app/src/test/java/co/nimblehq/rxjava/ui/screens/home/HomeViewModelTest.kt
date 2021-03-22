package co.nimblehq.rxjava.ui.screens.home

import co.nimblehq.rxjava.domain.data.error.DataError
import co.nimblehq.rxjava.domain.test.MockUtil
import co.nimblehq.rxjava.domain.usecase.GetExampleDataUseCase
import com.nhaarman.mockitokotlin2.any
import io.reactivex.Single
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val mockGetExampleDataUseCase = mock<GetExampleDataUseCase>()

    @Before
    fun setup() {
        When calling mockGetExampleDataUseCase.execute(any()) itReturns Single.just(MockUtil.data)
        viewModel = HomeViewModel(
            mockGetExampleDataUseCase
        )
    }

    @Test
    fun `When initializing, it should emit first data loading`() {
        val dataObserver = viewModel.data.test()
        val loadingObserver = viewModel.showLoading.test()

        dataObserver
            .assertValueCount(1)
            .assertValue(MockUtil.data)

        loadingObserver
            .assertNoErrors()
            .assertValues(false)
    }

    @Test
    fun `When refreshing again, it should emit second data loading`() {
        val dataObserver = viewModel.data.test()
        val loadingObserver = viewModel.showLoading.test()

        viewModel.input.refresh()

        dataObserver
            .assertValueCount(2)
            .assertValues(MockUtil.data, MockUtil.data)

        loadingObserver
            .assertNoErrors()
            .assertValues(false, true, false)
    }

    @Test
    fun `Should emit error event if get data error`() {
        When calling mockGetExampleDataUseCase.execute(any()) itReturns
            Single.error(DataError.GetDataError(null))
        val testObserver = viewModel.error.test()

        viewModel.input.refresh()

        testObserver
            .assertNoErrors()
            .assertValue { it is DataError.GetDataError }
    }
}
