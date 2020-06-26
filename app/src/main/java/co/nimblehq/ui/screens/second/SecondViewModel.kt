package co.nimblehq.ui.screens.second

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.ui.base.BaseViewModel
import co.nimblehq.ui.screens.data.Data
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class SecondViewModel @ViewModelInject constructor() : BaseViewModel(), Inputs, Outputs {

    val inputs: Inputs = this
    val outputs: Outputs = this

    private val _persistData = BehaviorSubject.create<Data>()
    override val persistData: Observable<Data>
        get() = _persistData

    override fun dataFromIntent(data: Data) {
        _persistData.onNext(data)
    }
}

interface Inputs {
    fun dataFromIntent(data: Data)
}

interface Outputs {
    val persistData: Observable<Data>
}
