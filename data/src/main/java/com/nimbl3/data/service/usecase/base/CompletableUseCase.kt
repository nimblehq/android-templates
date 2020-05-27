package com.nimbl3.data.service.usecase.base

import co.omise.gcpf.domain.data.error.AppError
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
                Completable.error(composeError(it))
            }
            .doOnError(::doOnError)
            .subscribeOn(executionThread)
            .observeOn(postExecutionThread)
    }

}
