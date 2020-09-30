package com.nimbl3.ui.base

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity: AppCompatActivity() {

    private var disposables = CompositeDisposable()

    protected fun Disposable.bindForDisposable() {
        disposables.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
