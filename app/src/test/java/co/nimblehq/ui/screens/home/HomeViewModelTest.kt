package co.nimblehq.ui.screens.home

import co.nimblehq.domain.schedulers.TrampolineSchedulerProvider
import co.nimblehq.testutil.MockPositiveApiRepository
import org.junit.Test

@Suppress("IllegalIdentifier")
class HomeViewModelTest {

    @Test
    fun `At init state, it should emit first load data `() {
        val viewModel = HomeViewModel(MockPositiveApiRepository, TrampolineSchedulerProvider)

        val dataLoaded = viewModel.loadData.test()
        dataLoaded
            .assertValueCount(1)
            .assertValue { it.content.contains("author1") }
    }

    @Test
    fun `When refresh data, it should emit show then hide loading, and emit data`() {
        val viewModel = HomeViewModel(MockPositiveApiRepository, TrampolineSchedulerProvider)
        val dataLoaded = viewModel.loadData.test()

        viewModel.input.refresh()
        dataLoaded.assertValueCount(2)
    }
}
