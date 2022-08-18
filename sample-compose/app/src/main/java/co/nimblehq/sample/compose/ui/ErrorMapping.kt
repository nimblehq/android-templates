package co.nimblehq.sample.compose.ui

import android.content.Context
import co.nimblehq.sample.compose.R

fun Throwable.userReadableMessage(context: Context): String {
    return context.getString(R.string.error_generic)
}
