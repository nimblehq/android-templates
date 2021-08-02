package co.nimblehq.rxjava.ui.screens.second

import co.nimblehq.rxjava.domain.data.Data
import co.nimblehq.rxjava.ui.base.BaseViewModel
import co.nimblehq.rxjava.ui.base.NavigationEvent
import co.nimblehq.rxjava.ui.screens.webview.WebViewBundle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

interface Input {
    fun dataFromIntent(data: Data)

    fun openPost()
}

@HiltViewModel
class SecondViewModel @Inject constructor() : BaseViewModel(), Input {

    val input: Input = this

    private val _data = BehaviorSubject.create<Data>()
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
