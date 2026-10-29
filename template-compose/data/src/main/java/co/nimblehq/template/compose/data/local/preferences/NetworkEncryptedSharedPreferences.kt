package co.nimblehq.template.compose.data.local.preferences

import android.content.Context
import co.nimblehq.template.compose.domain.models.AuthStatus
import java.security.GeneralSecurityException

private const val NETWORK_SECRET_SHARED_PREFS = "network_secret_shared_prefs"

private const val ACCESS_TOKEN = "ACCESS_TOKEN"
private const val TOKEN_TYPE = "TOKEN_TYPE"
private const val EXPIRES_IN = "EXPIRES_IN"
private const val REFRESH_TOKEN = "REFRESH_TOKEN"

class NetworkEncryptedSharedPreferences(
    applicationContext: Context,
) : BaseEncryptedSharedPreferences() {

    init {
        // Add try/catch to fix the crash on initializing: https://github.com/google/tink/issues/535
        sharedPreferences = try {
            createEncryptedSharedPreferences(NETWORK_SECRET_SHARED_PREFS, applicationContext)
        } catch (e: GeneralSecurityException) {
            // Workaround solution: https://github.com/google/tink/issues/535#issuecomment-912170221
            deleteMasterKeyEntry()
            deleteExistingPreferences(NETWORK_SECRET_SHARED_PREFS, applicationContext)
            createEncryptedSharedPreferences(NETWORK_SECRET_SHARED_PREFS, applicationContext)
        }
    }

    private val accessToken: String?
        get() = get(ACCESS_TOKEN)

    private val tokenType: String?
        get() = get(TOKEN_TYPE)

    private val expiresIn: Int?
        get() = get(EXPIRES_IN)

    private val refreshToken: String?
        get() = get(REFRESH_TOKEN)

    var authStatus: AuthStatus
        get() = if (accessToken.isNullOrEmpty()) {
            AuthStatus.Anonymous
        } else {
            AuthStatus.Authenticated(
                accessToken = accessToken.orEmpty(),
                tokenType = tokenType,
                expiresIn = expiresIn,
                refreshToken = refreshToken.orEmpty(),
            )
        }
        set(value) {
            // Write every field in one transaction, so readers never see a partial update
            sharedPreferences.execute(isCommitEnabled = true) { editor ->
                if (value is AuthStatus.Authenticated) {
                    editor.putString(ACCESS_TOKEN, value.accessToken)
                    editor.putString(TOKEN_TYPE, value.tokenType)
                    value.expiresIn?.let { editor.putInt(EXPIRES_IN, it) } ?: editor.remove(
                        EXPIRES_IN
                    )
                    editor.putString(REFRESH_TOKEN, value.refreshToken)
                } else {
                    // AuthStatus.Anonymous means logging out
                    editor.clear()
                }
            }
        }
}
