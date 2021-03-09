package co.nimblehq.extension

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Provide extension functions for RxJava related components
 */

fun CompositeDisposable.bind(disposable: Disposable) {
    this.add(disposable)
}
