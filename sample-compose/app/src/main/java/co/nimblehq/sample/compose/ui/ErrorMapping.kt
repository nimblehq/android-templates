package co.nimblehq.sample.compose.ui

import android.content.Context
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.domain.exceptions.ApiException
import co.nimblehq.sample.compose.extensions.showToast

fun Throwable.userReadableMessage(context: Context): String {
    return when (this) {
        is ApiException -> error?.message
        else -> message
    } ?: context.getString(R.string.error_generic)
}

fun Throwable.showToast(context: Context) =
    context.showToast(userReadableMessage(context))
