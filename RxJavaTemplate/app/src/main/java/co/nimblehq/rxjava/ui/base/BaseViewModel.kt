package co.nimblehq.rxjava.ui.base

import androidx.lifecycle.ViewModel
import co.nimblehq.rxjava.lib.IsLoading
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

@Suppress("PropertyName")
abstract class BaseViewModel : ViewModel() {

    private val _showLoading = BehaviorSubject.createDefault(false)
    protected val _error = BehaviorSubject.create<Throwable>()
    protected val _navigator = PublishSubject.create<NavigationEvent>()

    private val disposables by lazy { CompositeDisposable() }
    private var loadingCount: Int = 0

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

    /**
     * To show loading manually, should call `hideLoading` after
     */
    protected fun showLoading() {
        if (loadingCount == 0) {
            _showLoading.onNext(true)
        }
        loadingCount++
    }

    /**
     * To hide loading manually, should be called after `showLoading`
     */
    protected fun hideLoading() {
        loadingCount--
        if (loadingCount == 0) {
            _showLoading.onNext(false)
        }
    }

    /**
     * To manage show/hide loading automatically for Single operator
     */
    protected fun <T> Single<T>.doShowLoading(): Single<T> = this
        .doOnSubscribe { showLoading() }
        .doFinally { hideLoading() }

    /**
     * To manage show/hide loading automatically for Flowable operator
     */
    protected fun <T> Flowable<T>.doShowLoading(): Flowable<T> = this
        .doOnSubscribe { showLoading() }
        .doFinally { hideLoading() }

    /**
     * To manage show/hide loading automatically for Completable operator
     */
    protected fun Completable.doShowLoading(): Completable = this
        .doOnSubscribe { showLoading() }
        .doFinally { hideLoading() }

    protected fun Disposable.addToDisposables() = addTo(disposables)
}
