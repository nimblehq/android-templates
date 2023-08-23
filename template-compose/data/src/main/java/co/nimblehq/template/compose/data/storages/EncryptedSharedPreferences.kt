package co.nimblehq.template.compose.data.storages

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

private const val APP_SECRET_SHARED_PREFS = "app_secret_shared_prefs"

class EncryptedSharedPreferences(
    applicationContext: Context,
) : BaseSharedPreferences() {

    init {
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        sharedPreferences = EncryptedSharedPreferences.create(
            APP_SECRET_SHARED_PREFS,
            masterKey,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}
