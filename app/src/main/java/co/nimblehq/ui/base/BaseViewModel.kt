package co.nimblehq.ui.base

import androidx.lifecycle.ViewModel
import co.nimblehq.lib.IsLoading
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

@Suppress("PropertyName")
abstract class BaseViewModel : ViewModel() {

    protected val _showLoading = BehaviorSubject.create<IsLoading>()
    protected val _error = BehaviorSubject.create<Throwable>()
    protected val _navigator = PublishSubject.create<NavigationEvent>()

    private val disposables by lazy { CompositeDisposable() }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    val showLoading: Observable<IsLoading>
        get() = _showLoading

    val error: Observable<Throwable>
        get() = _error

    val navigator: Observable<NavigationEvent>
        get() = _navigator

    protected fun Disposable.addToDisposables() = addTo(disposables)
}
