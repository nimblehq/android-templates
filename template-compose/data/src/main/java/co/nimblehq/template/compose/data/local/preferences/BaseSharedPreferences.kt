package co.nimblehq.template.compose.data.local.preferences

import android.content.SharedPreferences

abstract class BaseSharedPreferences {

    lateinit var sharedPreferences: SharedPreferences

    inline fun <reified T> get(key: String): T? =
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

    fun <T> set(key: String, value: T, executeWithCommit: Boolean = false) {
        sharedPreferences.execute(executeWithCommit) {
            when (value) {
                is Boolean -> it.putBoolean(key, value)
                is String -> it.putString(key, value)
                is Float -> it.putFloat(key, value)
                is Long -> it.putLong(key, value)
                is Int -> it.putInt(key, value)
            }
        }
    }

    fun remove(key: String, executeWithCommit: Boolean = false) {
        sharedPreferences.execute(executeWithCommit) { it.remove(key) }
    }

    fun clearAll(executeWithCommit: Boolean = false) {
        sharedPreferences.execute(executeWithCommit) { it.clear() }
    }
}
