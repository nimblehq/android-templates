package co.nimblehq.sample.compose.extensions

import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.getAndRemove(key: String): T? {
    return if (contains(key)) {
        val value = get<T>(key)
        remove<T>(key)
        value
    } else null
}
