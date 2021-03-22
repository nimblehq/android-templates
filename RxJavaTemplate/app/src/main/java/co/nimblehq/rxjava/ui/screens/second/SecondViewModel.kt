package co.nimblehq.rxjava.ui.screens.second

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.rxjava.domain.data.Data
import co.nimblehq.rxjava.ui.base.BaseViewModel
import co.nimblehq.rxjava.ui.base.NavigationEvent
import co.nimblehq.rxjava.ui.screens.webview.WebViewBundle
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface Input {
    fun dataFromIntent(data: Data)

    fun openPost()
}

class SecondViewModel @ViewModelInject constructor() : BaseViewModel(), Input {

    private val _data = BehaviorSubject.create<Data>()

    val input: Input = this

    val data: Observable<Data>
        get() = _data

    override fun dataFromIntent(data: Data) {
        _data.onNext(data)
    }

    override fun openPost() {
        _navigator.onNext(
            NavigationEvent.WebView(
                WebViewBundle(_data.value?.url.orEmpty())
            )
        )
    }
}
