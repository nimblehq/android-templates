package com.nimbl3.ui.second

import com.nimbl3.data.lib.schedulers.SchedulersProvider
import com.nimbl3.ui.base.BaseViewModel
import com.nimbl3.ui.main.Const
import com.nimbl3.ui.main.data.Data
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

class SecondViewModel
@Inject constructor(private val schedulers: SchedulersProvider) : BaseViewModel(), Inputs, Outputs {

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

interface Inputs {
}

interface Outputs {
    fun setPersistedData(): Observable<Data>
}