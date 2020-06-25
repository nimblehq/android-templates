package co.nimblehq.domain.data.error

sealed class ValidateError(
    cause: Throwable?
) : AppError(cause) {

    class InvalidEmailError(cause: Throwable?) : ValidateError(cause)

    class InvalidPasswordError(cause: Throwable?) : ValidateError(cause)
}
