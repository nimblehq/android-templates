package co.nimblehq.ui.main

import co.nimblehq.testutil.MockPositiveApiRepository
import co.nimblehq.testutil.MockSchedulersProvider
import org.junit.Test

@Suppress("IllegalIdentifier")
class MainViewModelTest {

    @Test
    fun `At init state, it should emit first load data `() {
        val viewModel = MainViewModel(MockPositiveApiRepository, MockSchedulersProvider)

        val dataLoaded = viewModel.outputs.loadData().test()
        dataLoaded
            .assertValueCount(1)
            .assertValue { it.content.contains("author1") }

        viewModel
            .outputs.isLoading().test()
            .assertValues(false)
    }

    @Test
    fun `When refresh data, it should emit show then hide loading, and emit data`() {
        val viewModel = MainViewModel(MockPositiveApiRepository, MockSchedulersProvider)
        val dataLoaded = viewModel.outputs.loadData().test()
        val isLoading = viewModel.outputs.isLoading().test()

        isLoading
            .assertValueCount(1)
            .assertValue(false)

        viewModel.inputs.refresh()
        dataLoaded.assertValueCount(2)
        isLoading
            .assertValueCount(3)
            .assertValues(false, true, false)
    }
}
