package co.nimblehq.domain.data.error

sealed class ValidateError(
    override val cause: Throwable?
) : AppError(cause) {

    data class InvalidEmailError(override val cause: Throwable?) : ValidateError(cause)

    data class InvalidPasswordError(override val cause: Throwable?) : ValidateError(cause)
}
