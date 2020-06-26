package co.nimblehq.ui.screens.home

import co.nimblehq.data.lib.rxjava.transformers.Transformers
import co.nimblehq.data.service.response.ExampleResponse
import co.nimblehq.domain.repository.ApiRepository
import co.nimblehq.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

abstract class HomeViewModel : BaseViewModel() {

    abstract val input: Input

    abstract val loadData: Observable<Data>

    abstract val gotoNextScreen: Observable<Data>

    interface Input {

        fun refresh()

        fun next()
    }
}

class HomeViewModelImpl @Inject constructor(
    private val repository: ApiRepository,
    private val schedulers: BaseSchedulerProvider
) : HomeViewModel(), HomeViewModel.Input {

    private val _refresh = PublishSubject.create<Unit>()
    private val _next = PublishSubject.create<Unit>()
    private val _gotoNext = PublishSubject.create<Data>()

    private val _data = BehaviorSubject.create<Data>()

    init {
        fetchApi()
            .map { fromResponse(it) }
            .observeOn(schedulers.main())
            .subscribe({
                _data.onNext(it)
                _showLoading.onNext(false)
            }, {
                TODO("Handle Error  ¯\\_(ツ)_/¯ ")
            })
            .addToDisposables()

        _refresh
            .flatMap<ExampleResponse> { fetchApi() }
            .map { fromResponse(it) }
            .observeOn(schedulers.main())
            .subscribe({
                _data.onNext(it)
                _showLoading.onNext(false)
            }, {
                TODO("Handle Error  ¯\\_(ツ)_/¯ ")
            })
            .addToDisposables()

        _data
            .compose<Data>(Transformers.takeWhen(_next))
            .subscribeOn(schedulers.io())
            .subscribe(_gotoNext::onNext)
            .addToDisposables()
    }

    override val input: Input
        get() = this

    override val loadData: Observable<Data>
        get() = _data

    override val gotoNextScreen: Observable<Data>
        get() = _gotoNext

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
