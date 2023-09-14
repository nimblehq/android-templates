package co.nimblehq.sample.xml.ui

import android.content.Context
import co.nimblehq.sample.xml.R
import co.nimblehq.sample.xml.domain.exceptions.ApiException

fun Throwable.userReadableMessage(context: Context): String {
    return when (this) {
        is ApiException -> error?.message
        else -> message
    } ?: context.getString(R.string.error_generic)
}
