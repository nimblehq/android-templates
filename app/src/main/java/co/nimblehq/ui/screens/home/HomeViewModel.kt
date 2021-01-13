package co.nimblehq.ui.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.domain.data.Data
import co.nimblehq.domain.usecase.GetExampleDataUseCase
import co.nimblehq.ui.base.BaseViewModel
import co.nimblehq.ui.base.NavigationEvent
import co.nimblehq.ui.screens.second.SecondBundle
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject

interface Input {

    fun refresh()

    fun next()
}

class HomeViewModel @ViewModelInject constructor(
    private val getExampleDataUseCase: GetExampleDataUseCase
) : BaseViewModel(), Input {

    private val _data = BehaviorSubject.create<Data>()

    init {
        fetchApi()
    }

    val input = this

    val data: Observable<Data>
        get() = _data

    override fun refresh() {
        fetchApi()
    }

    override fun next() {
        _data
            .map {
                NavigationEvent.Second(SecondBundle(it))
            }
            .subscribeBy(
                onNext = _navigator::onNext,
                onError = _error::onNext
            )
            .addToDisposables()
    }

    private fun fetchApi() {
        getExampleDataUseCase
            .execute(Unit)
            .doShowLoading()
            .subscribeBy(
                onSuccess = _data::onNext,
                onError = _error::onNext
            )
            .addToDisposables()
    }
}
