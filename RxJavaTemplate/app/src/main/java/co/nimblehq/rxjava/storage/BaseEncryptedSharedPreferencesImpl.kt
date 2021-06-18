package co.nimblehq.rxjava.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import co.nimblehq.rxjava.extension.execute
import co.nimblehq.rxjava.extension.put
import javax.inject.Inject

// TODO: For demo purpose only, replace with actual value that needs to be stored securely
private const val PREF_TEST_ID = "PREF_TEST_ID"

class BaseEncryptedSharedPreferencesImpl @Inject constructor(applicationContext: Context) :
    BaseEncryptedSharedPreferences {

    private var encryptedSharedPreferences: SharedPreferences

    init {
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        encryptedSharedPreferences = EncryptedSharedPreferences.create(
            "app_secret_shared_prefs",
            masterKey,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // TODO: For demo purpose only, replace with actual value that needs to be stored securely
    override var testId: String?
        get() = encryptedSharedPreferences.getString(PREF_TEST_ID, null)
        set(value) {
            encryptedSharedPreferences.execute { it.put(PREF_TEST_ID to value) }
        }

    override fun clearAll() {
        encryptedSharedPreferences.execute { it.clear() }
    }
}
