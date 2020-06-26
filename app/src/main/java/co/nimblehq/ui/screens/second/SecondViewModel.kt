package co.nimblehq.ui.screens.second

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.ui.base.BaseViewModel
import co.nimblehq.ui.screens.home.Data
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

abstract class SecondViewModel : BaseViewModel() {

    abstract fun setPersistedData(): Observable<Data>
}

class SecondViewModelImpl @ViewModelInject constructor(
    private val schedulers: BaseSchedulerProvider
) : SecondViewModel() {

    private val _persistData = BehaviorSubject.create<Data>()

    init {
//        val dataFromIntent = intent()
//            .subscribeOn(schedulers.io())
//            .map { it.getParcelableExtra<Data>(Const.EXTRAS_DATA) }
//
//        dataFromIntent
//            .subscribe(persistData::onNext)
//            .bindForDisposable()
    }

    override fun setPersistedData() = this._persistData
}
