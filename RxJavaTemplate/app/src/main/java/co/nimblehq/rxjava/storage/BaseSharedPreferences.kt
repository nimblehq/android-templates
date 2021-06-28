package co.nimblehq.rxjava.storage

import android.content.SharedPreferences
import co.nimblehq.rxjava.extension.execute
import co.nimblehq.rxjava.extension.put

abstract class BaseSharedPreferences {

    protected lateinit var sharedPreferences: SharedPreferences

    protected fun put(key: String, value: Any?) {
        sharedPreferences.execute { it.put(key to value) }
    }

    protected fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    protected fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    protected fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    protected fun clearAll() {
        sharedPreferences.execute { it.clear() }
    }
}
