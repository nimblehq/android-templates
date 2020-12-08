package co.nimblehq.ui.second

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.ui.base.BaseViewModel
import co.nimblehq.ui.main.data.Data
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class SecondViewModel @ViewModelInject constructor() : BaseViewModel(), Inputs, Outputs {

    private val persistData = BehaviorSubject.create<Data>()

    val inputs: Inputs = this
    val outputs: Outputs = this

    override fun dataFromIntent(data: Data) {
        persistData.onNext(data)
    }

    override fun setPersistedData() = this.persistData
}

interface Inputs {
    fun dataFromIntent(data: Data)
}

interface Outputs {
    fun setPersistedData(): Observable<Data>
}
