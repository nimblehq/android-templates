package co.nimblehq.template.ui

import android.content.Context
import co.nimblehq.template.R
import co.nimblehq.template.domain.exceptions.ApiException

fun Throwable.userReadableMessage(context: Context): String {
    return when (this) {
        is ApiException -> error?.message
        else -> message
    } ?: context.getString(R.string.error_generic)
}
