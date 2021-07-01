package co.nimblehq.rxjava.domain.usecase.base

import co.nimblehq.rxjava.domain.data.error.AppError
import io.reactivex.Completable
import io.reactivex.Scheduler

abstract class CompletableUseCase<in UseCaseInput>(
    private val executionThread: Scheduler,
    private val postExecutionThread: Scheduler,
    defaultError: (Throwable) -> AppError
) : BaseUseCase(defaultError) {

    protected abstract fun create(input: UseCaseInput): Completable

    fun execute(input: UseCaseInput): Completable {
        return create(input)
            .onErrorResumeNext {
                if (isCanceledException(it)) {
                    Completable.never()
                } else {
                    Completable.error(composeError(it))
                }
            }
            .doOnError(::doOnError)
            .subscribeOn(executionThread)
            .observeOn(postExecutionThread)
    }

}
