package co.nimblehq.rxjava.ui.screens.webview

import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import co.nimblehq.rxjava.R
import co.nimblehq.rxjava.extension.initialSetup
import co.nimblehq.rxjava.extension.visibleOrGone
import co.nimblehq.rxjava.lib.IsLoading
import co.nimblehq.rxjava.ui.base.BaseFragment
import co.nimblehq.rxjava.ui.helpers.handleVisualOverlaps
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_webview.*
import kotlinx.android.synthetic.main.view_loading.*

@AndroidEntryPoint
class WebViewFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_webview

    private val viewModel by viewModels<WebViewViewModel>()
    private val args: WebViewFragmentArgs by navArgs()
    private val bundle: WebViewBundle by lazy { args.bundle }

    override fun setupView() {}

    override fun handleVisualOverlaps() {
        webView.handleVisualOverlaps()
    }

    override fun bindViewModel() {
        viewModel.showLoading bindTo ::bindLoading
        viewModel.error bindTo ::displayError
        viewModel.startUrl bindTo ::loadUrl

        viewModel.input.loadUrl(bundle.url)
    }

    private fun bindLoading(isLoading: IsLoading) {
        pbLoading.visibleOrGone(isLoading)
    }

    private fun loadUrl(startUrl: String) {
        val webChromeClient = AppWebChromeClient {
            viewModel.progress(it)
        }
        webView.initialSetup(WebViewClient(), webChromeClient)

        webView.loadUrl(startUrl)
    }
}
