package nimbl3.com.data.lib.rxjava.transformers;

import android.support.annotation.Nullable;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Consumer;

public final class FlowableNeverErrorTransformer<T> implements FlowableTransformer<T, T> {
    private Consumer mErrorConsumer;

    protected FlowableNeverErrorTransformer() {
        this.mErrorConsumer = null;
    }

    protected FlowableNeverErrorTransformer(final @Nullable Consumer<Throwable> errorConsumer) {
        this.mErrorConsumer = errorConsumer;
    }

    @Override
    public Publisher<T> apply(Flowable<T> source) {
        return source
            .doOnError(throwable -> {
                if (this.mErrorConsumer != null) {
                    this.mErrorConsumer.accept(throwable);
                }
            })
            .onErrorResumeNext(Flowable.empty());
    }
}
