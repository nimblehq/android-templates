package co.nimblehq.template.compose.data.local.preferences

import android.content.SharedPreferences

fun SharedPreferences.execute(
    isCommitEnabled: Boolean = false,
    operation: (SharedPreferences.Editor) -> Unit
) {
    with(edit()) {
        operation(this)
        if (isCommitEnabled) commit() else apply()
    }
}
