package co.nimblehq.rxjava.ui.screens.webview

import android.webkit.WebChromeClient
import android.webkit.WebView

class AppWebChromeClient(private val onProgress: (WebViewProgress) -> Unit) : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        val progress = when (newProgress) {
            PROGRESS_STARTED -> WebViewProgress.Show
            PROGRESS_COMPLETED -> WebViewProgress.Hide
            else -> WebViewProgress.Progress(newProgress)
        }
        onProgress.invoke(progress)
    }
}

/**
 * We separate the WebViewProgress to 3 states to keep the way to handle progress
 * showing/hiding or progress updating simple and easy.
 */
sealed class WebViewProgress {
    object Show : WebViewProgress()
    data class Progress(val progress: Int) : WebViewProgress()
    object Hide : WebViewProgress()
}

private const val PROGRESS_STARTED = 0
private const val PROGRESS_COMPLETED = 100
