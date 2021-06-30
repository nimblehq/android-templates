package co.nimblehq.rxjava.domain.storage

import android.content.SharedPreferences
import co.nimblehq.rxjava.domain.extension.execute

abstract class BaseSharedPreferences {

    protected lateinit var sharedPreferences: SharedPreferences

    protected fun <T> set(key: String, value: T?) {
        sharedPreferences.execute {
            when (value) {
                null -> it.remove(key)
                is Boolean -> it.putBoolean(key, value)
                is String -> it.putString(key, value)
                is Float -> it.putFloat(key, value)
                is Long -> it.putLong(key, value)
                is Int -> it.putInt(key, value)
            }
        }
    }

    protected inline fun <reified T> get(key: String): T? =
        if (sharedPreferences.contains(key)) {
            when (T::class) {
                Boolean::class -> sharedPreferences.getBoolean(key, false) as T?
                String::class -> sharedPreferences.getString(key, null) as T?
                Float::class -> sharedPreferences.getFloat(key, 0f) as T?
                Int::class -> sharedPreferences.getInt(key, 0) as T?
                Long::class -> sharedPreferences.getLong(key, 0L) as T?
                else -> null
            }
        } else {
            null
        }

    protected fun clearAll() {
        sharedPreferences.execute { it.clear() }
    }
}
