package co.nimblehq.coroutine.storage

import android.content.SharedPreferences

fun SharedPreferences.execute(operation: (SharedPreferences.Editor) -> Unit) {
    with(edit()) {
        operation(this)
        apply()
    }
}
