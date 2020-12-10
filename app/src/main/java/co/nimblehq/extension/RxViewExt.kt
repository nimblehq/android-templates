package co.nimblehq.extension

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * Subscribe to the [View]'s OnClick event, with debounce timeout of 200ms.
 * When an error happens, the stream isn't stopped, it just prints out the error with [Timber.e]
 */
fun View.subscribeOnClick(onNext: () -> Unit): Disposable {
    return clicks().throttleAndSubscribe { onNext() }
}

/**
 * Subscribe to the [RecyclerView]'s OnItemClick event, with debounce timeout of 200ms.
 * When an error happens, the stream isn't stopped, it just prints out the error with [Timber.e]
 */
fun <T> Observable<T>.subscribeOnItemClick(onNext: (T) -> Unit): Disposable {
    return this.throttleAndSubscribe(onNext)
}
