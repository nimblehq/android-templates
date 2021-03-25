package co.nimblehq.coroutine.domain.data.error

open class AppError(
    override val cause: Throwable?
) : Throwable(cause) {

    // TODO implement actual Readable message
    val readableMessage: String
        get() = "Readable message"
}
