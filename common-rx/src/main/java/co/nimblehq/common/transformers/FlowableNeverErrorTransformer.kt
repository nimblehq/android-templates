package co.nimblehq.common.transformers

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.functions.Consumer
import org.reactivestreams.Publisher

class FlowableNeverErrorTransformer<T>(
    private val errorConsumer: Consumer<Throwable>? = null
) : FlowableTransformer<T, T> {

    override fun apply(source: Flowable<T>): Publisher<T> {
        return source
            .doOnError { throwable ->
                errorConsumer?.accept(throwable)
            }
            .onErrorResumeNext(Flowable.empty()) // onComplete will be invoked
    }
}
