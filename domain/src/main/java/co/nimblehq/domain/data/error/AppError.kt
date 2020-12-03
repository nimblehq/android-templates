package co.nimblehq.domain.data.error

import co.nimblehq.data.service.error.JsonApiException

open class AppError(
    cause: Throwable?
) : Throwable(cause) {

    val readableMessage: String?
        get() = (cause as? JsonApiException)?.error?.message
}

class Ignored(cause: Throwable?) : AppError(cause)

class NoConnectivityError(cause: Throwable?) : AppError(cause)
