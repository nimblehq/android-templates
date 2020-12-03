package co.nimblehq.ui.second

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.data.lib.schedulers.SchedulersProvider
import co.nimblehq.ui.base.BaseViewModel
import co.nimblehq.ui.main.Const
import co.nimblehq.ui.main.data.Data
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class SecondViewModel
@ViewModelInject constructor(
    private val schedulers: SchedulersProvider
) : BaseViewModel(), Inputs, Outputs {

    private val persistData = BehaviorSubject.create<Data>()

    val inputs: Inputs = this
    val outputs: Outputs = this

    init {
        val dataFromIntent = intent()
            .subscribeOn(schedulers.io())
            .map { it.getParcelableExtra<Data>(Const.EXTRAS_DATA) }

        dataFromIntent
            .subscribe(persistData::onNext)
            .bindForDisposable()
    }

    override fun setPersistedData() = this.persistData!!
}

interface Inputs { }

interface Outputs {
    fun setPersistedData(): Observable<Data>
}
