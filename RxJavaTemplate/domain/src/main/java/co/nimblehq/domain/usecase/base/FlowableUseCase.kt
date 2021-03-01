package co.nimblehq.domain.usecase.base

import co.nimblehq.domain.data.error.AppError
import io.reactivex.Flowable
import io.reactivex.Scheduler

abstract class FlowableUseCase<in UseCaseInput, T>(
    private val executionThread: Scheduler,
    private val postExecutionThread: Scheduler,
    defaultError: (Throwable) -> AppError
) : BaseUseCase(defaultError) {

    protected abstract fun create(input: UseCaseInput): Flowable<T>

    fun execute(input: UseCaseInput): Flowable<T> {
        return create(input)
            .onErrorResumeNext { error: Throwable ->
                if (isCanceledException(error)) {
                    Flowable.never()
                } else {
                    Flowable.error(composeError(error))
                }
            }
            .doOnError(::doOnError)
            .subscribeOn(executionThread)
            .observeOn(postExecutionThread)
    }

}
