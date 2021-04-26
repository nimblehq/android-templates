package co.nimblehq.rxjava.ui.screens.home

import co.nimblehq.rxjava.domain.data.error.DataError
import co.nimblehq.rxjava.domain.test.MockUtil
import co.nimblehq.rxjava.domain.usecase.GetExampleDataUseCase
import co.nimblehq.rxjava.ui.base.NavigationEvent
import co.nimblehq.rxjava.ui.screens.second.SecondBundle
import com.nhaarman.mockitokotlin2.any
import io.reactivex.Single
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val mockGetExampleDataUseCase = mock<GetExampleDataUseCase>()

    @Before
    fun setup() {
        When calling mockGetExampleDataUseCase.execute(any()) itReturns Single.just(MockUtil.dataList)
        viewModel = HomeViewModel(mockGetExampleDataUseCase)
    }

    @Test
    fun `When initializing HomeViewModel, it emits the first data correspondingly`() {
        val dataObserver = viewModel.data.test()

        dataObserver
            .assertValueCount(1)
            .assertValue(MockUtil.dataList)
    }

    @Test
    fun `When initializing HomeViewModel, it doesn't emit to show any loading indicator, defaulting to false`() {
        val loadingObserver = viewModel.showLoading.test()

        loadingObserver
            .assertNoErrors()
            .assertValue(false)
    }

    @Test
    fun `When calling refresh responds positive result, it emits the second data correspondingly`() {
        val dataObserver = viewModel.data.test()

        viewModel.input.refresh()

        dataObserver
            .assertValueCount(2)
            .assertValues(MockUtil.dataList, MockUtil.dataList)
    }

    @Test
    fun `When calling refresh regardless of success or failure, it emits to 2 new states to showLoading as true then false`() {
        val loadingObserver = viewModel.showLoading.test()

        viewModel.input.refresh()

        loadingObserver
            .assertNoErrors()
            .assertValues(false, true, false)
    }

    @Test
    fun `When calling refresh responds any negative result, it emits the corresponding error`() {
        When calling mockGetExampleDataUseCase.execute(any()) itReturns
            Single.error(DataError.GetDataError(null))
        val testObserver = viewModel.error.test()

        viewModel.input.refresh()

        testObserver
            .assertNoErrors()
            .assertValue { it is DataError.GetDataError }
    }

    @Test
    fun `When click on an item, it navigates to Second screen correctly`() {
        val navigatorObserver = viewModel.navigator.test()

        viewModel.navigateToDetail(MockUtil.dataList[0])

        navigatorObserver
            .assertValueCount(1)
            .assertValue(
                NavigationEvent.Second(
                    SecondBundle(MockUtil.dataList[0])
                )
            )
    }
}
