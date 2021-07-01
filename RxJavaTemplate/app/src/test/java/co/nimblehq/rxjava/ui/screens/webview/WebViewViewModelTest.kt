package co.nimblehq.rxjava.ui.screens.webview

import org.junit.Before
import org.junit.Test

class WebViewViewModelTest {

    private lateinit var viewModel: WebViewViewModel

    @Before
    fun setup() {
        viewModel = WebViewViewModel()
    }

    @Test
    fun `When loading url responds positive result, it emits the success value accordingly`() {
        val testObserver = viewModel.startUrl.test()

        viewModel.loadUrl("url")

        testObserver
            .assertNoErrors()
            .assertValue("url")
    }

    @Test
    fun `When different progresses are updated as Show, 50 percent and Hide sequentially, it emits showLoading events accordingly to the published progresses`() {
        val loadingObserver = viewModel.showLoading.test()

        viewModel.progress(WebViewProgress.Show)

        loadingObserver
            .assertNoErrors()
            .assertValues(false, true)

        viewModel.progress(WebViewProgress.Progress(50))

        loadingObserver
            .assertNoErrors()
            .assertValues(false, true)

        viewModel.progress(WebViewProgress.Hide)

        loadingObserver
            .assertNoErrors()
            .assertValues(false, true, false)
    }
}
