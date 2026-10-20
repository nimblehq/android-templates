package co.nimblehq.template.compose.data.local.preferences

import android.content.SharedPreferences

fun SharedPreferences.execute(
    executeWithCommit: Boolean = false,
    operation: (SharedPreferences.Editor) -> Unit
) {
    with(edit()) {
        operation(this)
        if (executeWithCommit) commit() else apply()
    }
}
