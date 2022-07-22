package co.nimblehq.template.ui

import android.content.Context
import co.nimblehq.template.R

fun Throwable.userReadableMessage(context: Context): String {
    return context.getString(R.string.error_generic)
}
