package co.nimblehq.coroutine.ui

import android.content.Context
import co.nimblehq.coroutine.R
import co.nimblehq.coroutine.domain.data.error.AppError

fun Throwable.userReadableMessage(context: Context): String {
    return (this as? AppError)?.readableMessage ?: context.getString(R.string.error_generic)
}
