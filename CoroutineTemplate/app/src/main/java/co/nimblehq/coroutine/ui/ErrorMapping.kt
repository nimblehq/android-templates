package co.nimblehq.coroutine.ui

import android.content.Context
import co.nimblehq.coroutine.R

fun Throwable.userReadableMessage(context: Context): String {
    // TODO implement user readable message
    return context.getString(R.string.error_generic)
}
