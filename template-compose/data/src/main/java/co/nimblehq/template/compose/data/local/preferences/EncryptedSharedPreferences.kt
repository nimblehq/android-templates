package co.nimblehq.template.compose.data.local.preferences

import android.content.Context
import javax.inject.Inject

private const val APP_SECRET_SHARED_PREFS = "app_secret_shared_prefs"

class EncryptedSharedPreferences @Inject constructor(applicationContext: Context) :
    BaseEncryptedSharedPreferences() {

    init {
        sharedPreferences = createEncryptedSharedPreferences(
            fileName = APP_SECRET_SHARED_PREFS,
            context = applicationContext
        )
    }
}
