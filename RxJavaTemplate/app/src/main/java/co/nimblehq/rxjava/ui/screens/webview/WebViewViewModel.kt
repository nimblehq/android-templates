package co.nimblehq.rxjava.ui.screens.webview

import co.nimblehq.rxjava.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

interface Input {
    fun loadUrl(url: String)
}

class WebViewViewModel @Inject constructor() : BaseViewModel(), Input {

    private val _startUrl = BehaviorSubject.create<String>()

    val input: Input = this

    val startUrl: Observable<String>
        get() = _startUrl

    override fun loadUrl(url: String) {
        _startUrl.onNext(url)
    }

    fun progress(webViewProgress: WebViewProgress) {
        if (webViewProgress is WebViewProgress.Show) {
            if (_showLoading.value == false) showLoading()
        } else hideLoading()
    }
}
