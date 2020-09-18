package com.nimbl3.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

@AndroidEntryPoint
abstract class BaseFragment: Fragment() {
    private var disposables = CompositeDisposable()

    override fun onDetach() {
        super.onDetach()
        disposables.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    protected fun Disposable.bindForDisposable() {
        disposables.add(this)
    }
}
