package co.nimblehq.template.compose.ui

import android.content.Context
import android.widget.Toast
import co.nimblehq.template.compose.R
import co.nimblehq.template.compose.domain.exceptions.ApiException

fun Throwable.userReadableMessage(context: Context): String {
    return when (this) {
        is ApiException -> error?.message
        else -> message
    } ?: context.getString(R.string.error_generic)
}

fun Throwable.showToast(context: Context) =
    Toast.makeText(context, userReadableMessage(context), Toast.LENGTH_SHORT).show()
