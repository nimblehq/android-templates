package co.nimblehq.extension

import android.util.Log
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.disposables.Disposable
import java.util.concurrent.*

/**
 * An utility method to help preventing the Observable source to emit multiple times (debounce)
 * within the [DEFAULT_DEBOUNCE_TIME] amount of time.
 *
 * Example: This is helpful to <b>prevent multiple click events</b> triggering the emission.
 *
 * @param: [scheduler] where to manage the timers that handle timeout for each event
 * @return: an [Observable] that performs the throttle operation.
 */
fun <T> Observable<T>.debounce(scheduler: Scheduler): Observable<T> {
    return this.throttleFirst(DEFAULT_DEBOUNCE_TIME, TimeUnit.MILLISECONDS, scheduler)
}

/**
 * Subscribe to the Observable<T>, with debounce timeout of 200ms.
 * When an error happens, the stream isn't stopped, it just prints out the error with [Log.e]
 */
fun <T> Observable<T>.debounceAndSubscribe(onNext: (T) -> Unit): Disposable {
    return this
        .debounce(mainThread())
        .flatMapSingle {
            Single.fromCallable { onNext(it) }
                .onErrorReturn {
                    Log.e("debounceAndSubscribe", it.message ?: "Unknown error")
                }
        }
        .subscribe()
}

// Default debounce time for an event emission, avoid duplicated clicks.
const val DEFAULT_DEBOUNCE_TIME = 200L
