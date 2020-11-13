package co.nimblehq.common.transformers

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction

class TakeWhenTransformer<S, in T> constructor(private val observable: Observable<in T>) : ObservableTransformer<S, S> {

    override fun apply(upstream: Observable<S>): ObservableSource<S> {
        return observable.withLatestFrom(upstream, BiFunction { _, t2 -> t2 })
    }
}
