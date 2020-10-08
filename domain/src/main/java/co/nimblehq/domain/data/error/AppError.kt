package co.nimblehq.domain.data.error

import com.nimbl3.data.service.error.JsonApiException

open class AppError(
    cause: Throwable?
) : Throwable(cause) {

    val readableMessage: String?
        get() = (cause as? JsonApiException)?.error?.message
}

class Ignored(cause: Throwable?) : AppError(cause)

class NoConnectivityError(cause: Throwable?) : AppError(cause)
