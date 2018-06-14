package com.nimbl3.ui.base

import android.arch.lifecycle.ViewModel
import android.content.Intent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val intent = BehaviorSubject.create<Intent>()

    override fun onCleared() {
        Timber.d("Trung onClearViewModel")
        super.onCleared()
        disposables.clear()
    }

    protected fun Disposable.bindForDisposable() {
        disposables.add(this)
    }

    /**
     * The intent with its bundle taken over from the Activity
     */
    protected fun intent(): Observable<Intent> = this.intent

    /**
     * Emitting the Intent data to the corresponding ViewModel of a View for processing
     */
    fun intent(intent: Intent) {
        this.intent.onNext(intent)
    }
}