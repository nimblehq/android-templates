package com.nimbl3.data.service.usecase.base

import co.omise.gcpf.domain.data.error.AppError
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
                Flowable.error(composeError(error))
            }
            .doOnError(::doOnError)
            .subscribeOn(executionThread)
            .observeOn(postExecutionThread)
    }

}
