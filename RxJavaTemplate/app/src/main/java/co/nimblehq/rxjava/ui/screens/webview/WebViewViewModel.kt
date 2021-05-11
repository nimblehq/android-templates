package co.nimblehq.rxjava.ui.screens.webview

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.rxjava.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface Input {
    fun loadUrl(url: String)
}

class WebViewViewModel @ViewModelInject constructor() : BaseViewModel(), Input {

    val input: Input = this

    private val _startUrl = BehaviorSubject.create<String>()
    val startUrl: Observable<String>
        get() = _startUrl

    override fun loadUrl(url: String) {
        _startUrl.onNext(url)
    }

    fun progress(webViewProgress: WebViewProgress) {
        when (webViewProgress) {
            is WebViewProgress.Show -> showLoading()
            is WebViewProgress.Hide -> hideLoading()
            else -> Unit // we don't show percent progress yet
        }
    }
}
