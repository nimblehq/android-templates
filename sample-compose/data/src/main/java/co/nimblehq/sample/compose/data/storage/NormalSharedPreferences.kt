package co.nimblehq.sample.compose.data.storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class NormalSharedPreferences @Inject constructor(
    private val applicationContext: Context,
    private val defaultSharedPreferences: SharedPreferences
) : BaseSharedPreferences() {

    init {
        useDefaultSharedPreferences()
    }

    fun useDefaultSharedPreferences() {
        sharedPreferences = defaultSharedPreferences
    }

    // Use this function for creating a custom sharedPreferences if needed
    fun useCustomSharedPreferences(name: String) {
        sharedPreferences = applicationContext.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}
