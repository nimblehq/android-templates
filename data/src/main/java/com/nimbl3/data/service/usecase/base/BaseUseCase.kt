package com.nimbl3.data.service.usecase.base

import co.omise.gcpf.domain.BuildConfig
import co.omise.gcpf.domain.data.error.*
import co.omise.gcpf.service.errors.NoConnectivityException

abstract class BaseUseCase(
    private val defaultError: (Throwable) -> AppError
) {
    protected fun composeError(error: Throwable): Throwable = when (error) {
        is AppError -> error
        is NoConnectivityException -> NoConnectivityError(error)
        else -> defaultError(error)
    }

    protected fun doOnError(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()
        }
    }

}
