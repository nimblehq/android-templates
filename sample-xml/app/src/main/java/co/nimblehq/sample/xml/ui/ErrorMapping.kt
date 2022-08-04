package co.nimblehq.sample.xml.ui

import android.content.Context
import co.nimblehq.sample.xml.R

fun Throwable.userReadableMessage(context: Context): String {
    return context.getString(R.string.error_generic)
}
