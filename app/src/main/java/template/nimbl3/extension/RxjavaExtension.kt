package template.nimbl3.extension

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Provide extension functions for RxJava related components
 */

inline fun CompositeDisposable.bind(disposable: Disposable) {
    this.add(disposable)
}
