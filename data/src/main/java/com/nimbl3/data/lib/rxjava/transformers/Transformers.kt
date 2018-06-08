package com.nimbl3.data.lib.rxjava.transformers

import io.reactivex.Observable

object Transformers {

    /**
     * Emits the latest value of the `source` Observable whenever the `when`
     * Observable emits.
     */
    fun <S, T> takeWhen(`when`: Observable<T>): TakeWhenTransformer<S, T> {
        return TakeWhenTransformer(`when`)
    }
}
