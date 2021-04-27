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
    fun `Should emit url with start with explicit Url`() {
        val testObserver = viewModel.startUrl.test()

        viewModel.loadUrl("url")

        testObserver
            .assertNoErrors()
            .assertValue("url")
    }

    @Test
    fun `Should emit Loading event to react on loading progress submitted`() {
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
