package co.nimblehq.ui.screens.home

import co.nimblehq.domain.test.MockUtil
import co.nimblehq.domain.usecase.GetExampleDataUseCase
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
    fun `When initializing, it should emit first load data`() {
        val dataObserver = viewModel.data.test()

        dataObserver
            .assertValueCount(1)
            .assertValue { it.content.contains("author1") }
    }

    @Test
    fun `When refreshing data, it should emit show then hide loading, and emit data`() {
        val dataObserver = viewModel.data.test()

        viewModel.input.refresh()
        dataObserver.assertValueCount(2)
    }
}
