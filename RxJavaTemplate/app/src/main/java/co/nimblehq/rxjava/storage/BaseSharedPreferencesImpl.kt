package co.nimblehq.rxjava.storage

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import co.nimblehq.rxjava.extension.execute
import co.nimblehq.rxjava.extension.put
import javax.inject.Inject

// TODO: For demo purpose only, replace with actual value that needs to be stored securely
private const val PREF_TEST_ID = "PREF_TEST_ID"

class BaseSharedPreferencesImpl @Inject constructor(applicationContext: Context) :
    BaseSharedPreferences {

    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = defaultPreference(applicationContext)
    }

    // TODO: For demo purpose only, replace with actual value that needs to be stored securely
    override var testId: String?
        get() = sharedPreferences.getString(PREF_TEST_ID, null)
        set(value) {
            sharedPreferences.execute { it.put(PREF_TEST_ID to value) }
        }

    override fun clearAll() {
        sharedPreferences.execute { it.clear() }
    }

    private fun defaultPreference(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    // Can use this custom preference if needed
    private fun customPreference(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
}
