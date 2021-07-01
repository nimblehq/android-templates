package co.nimblehq.rxjava.extension

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.*

fun WebView.initialSetup(
    webViewClient: WebViewClient,
    webChromeClient: WebChromeClient
) {
    initSettings(settings)
    clearHistory()
    setWebViewClient(webViewClient)
    setWebChromeClient(webChromeClient)
    setInitialScale(INITIAL_SCALE)
}

@SuppressLint("SetJavaScriptEnabled")
private fun initSettings(settings: WebSettings) = with(settings) {
    builtInZoomControls = true
    displayZoomControls = false

    javaScriptEnabled = true
    loadWithOverviewMode = true
    useWideViewPort = true
    setAppCacheEnabled(false)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        safeBrowsingEnabled = false
    }
}

private const val INITIAL_SCALE = 100
