package co.nimblehq.domain.usecase.base

import co.nimblehq.data.service.error.NoConnectivityException
import co.nimblehq.domain.BuildConfig
import co.nimblehq.domain.data.error.AppError
import co.nimblehq.domain.data.error.NoConnectivityError
import okhttp3.internal.http2.StreamResetException
import java.io.IOException

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

    /** True if the request was canceled. */
    protected fun isCanceledException(error: Throwable) =
        isCanceledIOException(error) || isCanceledStreamResetException(error)

    private fun isCanceledIOException(error: Throwable) =
        error is IOException && error.message == MESSAGE_REQUEST_CANCELED

    private fun isCanceledStreamResetException(error: Throwable) =
        error is StreamResetException && error.message == MESSAGE_STREAM_WAS_RESET_CANCEL

}

private const val MESSAGE_REQUEST_CANCELED = "Canceled"
private const val MESSAGE_STREAM_WAS_RESET_CANCEL = "stream was reset: CANCEL"
