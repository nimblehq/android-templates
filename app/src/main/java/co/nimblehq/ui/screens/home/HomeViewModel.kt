package co.nimblehq.ui.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.common.transformers.Transformers
import co.nimblehq.data.service.response.ExampleResponse
import co.nimblehq.domain.repository.ApiRepository
import co.nimblehq.domain.schedulers.BaseSchedulerProvider
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
    private val repository: ApiRepository,
    private val schedulers: BaseSchedulerProvider
) : BaseViewModel(), Input {

    private val _refresh = PublishSubject.create<Unit>()
    private val _next = PublishSubject.create<Unit>()

    private val _data = BehaviorSubject.create<Data>()

    init {
        fetchApi()
            .map { fromResponse(it) }
            .observeOn(schedulers.main())
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
            .map { fromResponse(it) }
            .observeOn(schedulers.main())
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
            .subscribeOn(schedulers.io())
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

    val loadData: Observable<Data>
        get() = _data

    override fun refresh() {
        _refresh.onNext(Unit)
    }

    override fun next() {
        _next.onNext(Unit)
    }

    private fun fetchApi(): Observable<ExampleResponse> =
        repository
            .getExampleData()
            .subscribeOn(schedulers.io())
            .doOnSubscribe { _showLoading.onNext(true) }
            .toObservable()

    private fun fromResponse(response: ExampleResponse): Data {
        var content = ""
        (0..2)
            .map { response.data.children[it].data }
            .forEach {
                content += "Author = ${it.author} \nTitle = ${it.title} \n\n"
            }

        // Image from a random place
        val imageUrl = "http://www.monkeyuser.com/assets/images/2018/80-the-struggle.png"
        return Data(content, imageUrl)
    }
}
