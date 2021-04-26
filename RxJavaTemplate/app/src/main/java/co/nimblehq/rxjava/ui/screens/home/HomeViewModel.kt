package co.nimblehq.rxjava.ui.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.rxjava.domain.data.Data
import co.nimblehq.rxjava.domain.usecase.GetExampleDataUseCase
import co.nimblehq.rxjava.ui.base.BaseViewModel
import co.nimblehq.rxjava.ui.base.NavigationEvent
import co.nimblehq.rxjava.ui.screens.second.SecondBundle
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject

interface Input {

    fun refresh()

    fun navigateToDetail(data: Data)
}

class HomeViewModel @ViewModelInject constructor(
    private val getExampleDataUseCase: GetExampleDataUseCase
) : BaseViewModel(), Input {

    val input: Input = this

    init {
        fetchApi()
    }

    private val _data = BehaviorSubject.create<List<Data>>()
    val data: Observable<List<Data>>
        get() = _data

    override fun refresh() {
        fetchApi()
    }

    override fun navigateToDetail(data: Data) {
        _navigator.onNext(
            NavigationEvent.Second(SecondBundle(data))
        )
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
