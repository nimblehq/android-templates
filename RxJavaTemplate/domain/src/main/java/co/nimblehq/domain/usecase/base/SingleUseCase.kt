package co.nimblehq.domain.usecase.base

import co.nimblehq.domain.data.error.AppError
import io.reactivex.Scheduler
import io.reactivex.Single

abstract class SingleUseCase<in UseCaseInput, T>(
    private val executionThread: Scheduler,
    private val postExecutionThread: Scheduler,
    defaultError: (Throwable) -> AppError
) : BaseUseCase(defaultError) {

    protected abstract fun create(input: UseCaseInput): Single<T>

    fun execute(input: UseCaseInput): Single<T> {
        return create(input)
            .onErrorResumeNext {
                if (isCanceledException(it)) {
                    Single.never()
                } else {
                    Single.error(composeError(it))
                }
            }
            .doOnError(::doOnError)
            .subscribeOn(executionThread)
            .observeOn(postExecutionThread)
    }

}
