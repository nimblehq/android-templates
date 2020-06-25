package co.nimblehq.extension

import android.content.Context
import co.nimblehq.R
import co.nimblehq.domain.data.error.AppError
import co.nimblehq.domain.data.error.ValidateError

fun Throwable.userReadableMessage(context: Context, vararg formatArgs: Any?): String {
    val customErrorMessages = when (this) {
        is ValidateError.InvalidEmailError -> context.getString(R.string.error_email_invalid)
        is ValidateError.InvalidPasswordError -> context.getString(
            R.string.error_password_invalid,
            formatArgs[0].toString()
        )
        else -> null
    }
    return customErrorMessages
        ?: (this as? AppError)?.readableMessage
        ?: context.getString(R.string.error_generic)
}
