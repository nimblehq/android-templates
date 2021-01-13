package co.nimblehq.ui.screens.second

import co.nimblehq.domain.test.MockUtil
import org.junit.Before
import org.junit.Test

class SecondViewModelTest {

    private lateinit var viewModel: SecondViewModel

    @Before
    fun setup() {
        viewModel = SecondViewModel()
    }

    @Test
    fun `When initializing, it binds data correctly`() {
        val dataLoaded = viewModel.data.test()

        viewModel.dataFromIntent(MockUtil.data)

        dataLoaded
            .assertValueCount(1)
            .assertValue(MockUtil.data)
    }
}
