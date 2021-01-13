package co.nimblehq.ui.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.common.transformers.Transformers
import co.nimblehq.domain.data.Data
import co.nimblehq.domain.usecase.GetExampleDataUseCase
import co.nimblehq.ui.base.BaseViewModel
import co.nimblehq.ui.base.NavigationEvent
import co.nimblehq.ui.screens.second.SecondBundle
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

interface Input {

    fun refresh()

    fun next()
}

class HomeViewModel @ViewModelInject constructor(
    private val getExampleDataUseCase: GetExampleDataUseCase
) : BaseViewModel(), Input {

    private val _refresh = PublishSubject.create<Unit>()
    private val _next = PublishSubject.create<Unit>()

    private val _data = BehaviorSubject.create<Data>()

    init {
        fetchApi()
            .subscribeBy(
                onNext = {
                    _data.onNext(it)
                    _showLoading.onNext(false)
                },
                onError = _error::onNext
            )
            .addToDisposables()

        _refresh
            .flatMap { fetchApi() }
            .subscribeBy(
                onNext = {
                    _data.onNext(it)
                    _showLoading.onNext(false)
                },
                onError = _error::onNext
            )
            .addToDisposables()

        _data
            .compose(Transformers.takeWhen(_next))
            .map {
                NavigationEvent.Second(SecondBundle(it))
            }
            .subscribeBy(
                onNext = _navigator::onNext,
                onError = _error::onNext
            )
            .addToDisposables()
    }

    val input = this

    val data: Observable<Data>
        get() = _data

    override fun refresh() {
        _refresh.onNext(Unit)
    }

    override fun next() {
        _next.onNext(Unit)
    }

    private fun fetchApi(): Observable<Data> =
        getExampleDataUseCase
            .execute(Unit)
            .doOnSubscribe { _showLoading.onNext(true) }
            .toObservable()
}
