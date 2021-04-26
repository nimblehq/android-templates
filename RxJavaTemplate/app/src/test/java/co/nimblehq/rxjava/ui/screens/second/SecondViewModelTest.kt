package co.nimblehq.rxjava.ui.screens.second

import co.nimblehq.rxjava.domain.test.MockUtil
import co.nimblehq.rxjava.ui.base.NavigationEvent
import co.nimblehq.rxjava.ui.screens.webview.WebViewBundle
import org.junit.Before
import org.junit.Test

class SecondViewModelTest {

    private lateinit var viewModel: SecondViewModel

    @Before
    fun setup() {
        viewModel = SecondViewModel()
    }

    @Test
    fun `When calling dataFromIntent responds positive result, it emits success data correspondingly`() {
        val dataObserver = viewModel.data.test()

        viewModel.dataFromIntent(MockUtil.dataList[0])

        dataObserver
            .assertValueCount(1)
            .assertValue(MockUtil.dataList[0])
    }

    @Test
    fun `When open a post, it navigates to WebView screen correctly`() {
        val navigatorObserver = viewModel.navigator.test()

        viewModel.dataFromIntent(MockUtil.dataList[0])
        viewModel.openPost()

        navigatorObserver
            .assertValueCount(1)
            .assertValue(
                NavigationEvent.WebView(
                    WebViewBundle("url")
                )
            )
    }
}
