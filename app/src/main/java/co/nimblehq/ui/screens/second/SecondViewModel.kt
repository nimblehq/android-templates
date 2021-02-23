package co.nimblehq.ui.screens.second

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.domain.data.Data
import co.nimblehq.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class SecondViewModel @ViewModelInject constructor() : BaseViewModel(), Input {

    private val _data = BehaviorSubject.create<Data>()

    val input = this

    val data: Observable<Data>
        get() = _data

    override fun dataFromIntent(data: Data) {
        _data.onNext(data)
    }
}

interface Input {

    fun dataFromIntent(data: Data)
}
