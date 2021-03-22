package co.nimblehq.rxjava.ui.base

import co.nimblehq.rxjava.ui.screens.second.SecondBundle
import co.nimblehq.rxjava.ui.screens.webview.WebViewBundle

sealed class NavigationEvent {
    data class Second(val bundle: SecondBundle) : NavigationEvent()
    data class WebView(val bundle: WebViewBundle) : NavigationEvent()
}
