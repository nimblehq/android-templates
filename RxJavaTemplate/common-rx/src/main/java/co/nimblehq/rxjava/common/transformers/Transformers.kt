package co.nimblehq.rxjava.common.transformers

import io.reactivex.Observable

object Transformers {

    /**
     * Emits the latest value of the `source` Observable whenever the `when`
     * Observable emits.
     */
    fun <S, T> takeWhen(observable: Observable<T>): TakeWhenTransformer<S, T> {
        return TakeWhenTransformer(observable)
    }
}
