package co.nimblehq.ui.screens.second

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.domain.data.Data
import co.nimblehq.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class SecondViewModel @ViewModelInject constructor() : BaseViewModel(), Input, Output {

    val input = this
    val output = this

    private val _persistData = BehaviorSubject.create<Data>()
    override val persistData: Observable<Data>
        get() = _persistData

    override fun dataFromIntent(data: Data) {
        _persistData.onNext(data)
    }
}

interface Input {
    fun dataFromIntent(data: Data)
}

interface Output {
    val persistData: Observable<Data>
}
