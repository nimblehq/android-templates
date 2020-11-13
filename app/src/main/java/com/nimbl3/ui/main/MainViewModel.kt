package com.nimbl3.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.common.transformers.Transformers
import com.nimbl3.data.lib.schedulers.SchedulersProvider
import com.nimbl3.data.service.ApiRepository
import com.nimbl3.data.service.response.ExampleResponse
import com.nimbl3.lib.IsLoading
import com.nimbl3.ui.base.BaseViewModel
import com.nimbl3.ui.main.data.Data
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class MainViewModel
@ViewModelInject constructor(
    private val repository: ApiRepository,
    private val schedulers: SchedulersProvider
) : BaseViewModel(), Inputs, Outputs {

    private val refresh = PublishSubject.create<Unit>()
    private val next = PublishSubject.create<Unit>()
    private val gotoNext = PublishSubject.create<Data>()

    private val data = BehaviorSubject.create<Data>()
    private val isLoading = BehaviorSubject.create<IsLoading>()

    val inputs: Inputs = this
    val outputs: Outputs = this

    init {
        fetchApi()
            .map { fromResponse(it) }
            .observeOn(schedulers.main())
            .subscribe({
                data.onNext(it)
                isLoading.onNext(false)
            }, {
                TODO("Handle Error  ¯\\_(ツ)_/¯ ")
            })
            .bindForDisposable()

        refresh
            .flatMap<ExampleResponse> { fetchApi() }
            .map { fromResponse(it) }
            .observeOn(schedulers.main())
            .subscribe({
                data.onNext(it)
                isLoading.onNext(false)
            }, {
                TODO("Handle Error  ¯\\_(ツ)_/¯ ")
            })
            .bindForDisposable()

        data
            .compose<Data>(Transformers.takeWhen(next))
            .subscribeOn(schedulers.io())
            .subscribe(gotoNext::onNext)
            .bindForDisposable()
    }

    private fun fetchApi(): Observable<ExampleResponse> =
        repository
            .getExampleData()
            .subscribeOn(schedulers.io())
            .doOnSubscribe { isLoading.onNext(true) }
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

    override fun refresh() {
        refresh.onNext(Unit)
    }

    override fun next() {
        next.onNext(Unit)
    }

    override fun loadData(): Observable<Data> = this.data

    override fun isLoading(): Observable<IsLoading> = this.isLoading

    override fun gotoNextScreen(): Observable<Data> = this.gotoNext
}

interface Inputs {
    fun refresh()

    fun next()
}

interface Outputs {
    fun loadData(): Observable<Data>

    fun isLoading(): Observable<IsLoading>

    fun gotoNextScreen(): Observable<Data>
}
