package co.nimblehq.template.compose.data.local.preferences

import android.content.Context
import co.nimblehq.template.compose.domain.models.AuthStatus
import java.security.GeneralSecurityException

private const val NETWORK_SECRET_SHARED_PREFS = "network_secret_shared_prefs"

private const val ACCESS_TOKEN = "ACCESS_TOKEN"
private const val TOKEN_TYPE = "TOKEN_TYPE"
private const val EXPIRES_IN = "EXPIRES_IN"
private const val REFRESH_TOKEN = "REFRESH_TOKEN"

class NetworkEncryptedSharedPreferences constructor(
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

    private var accessToken: String?
        get() = get(ACCESS_TOKEN)
        set(value) = if (value == null) {
            remove(
                key = ACCESS_TOKEN,
                executeWithCommit = true
            )
        } else {
            set(
                key = ACCESS_TOKEN,
                value = value,
                executeWithCommit = true
            )
        }

    private var tokenType: String?
        get() = get(TOKEN_TYPE)
        set(value) = if (value == null) {
            remove(
                key = TOKEN_TYPE,
                executeWithCommit = true
            )
        } else {
            set(
                key = TOKEN_TYPE,
                value = value,
                executeWithCommit = true
            )
        }

    private var expiresIn: Int?
        get() = get(EXPIRES_IN)
        set(value) = if (value == null) {
            remove(
                key = EXPIRES_IN,
                executeWithCommit = true
            )
        } else {
            set(
                key = EXPIRES_IN,
                value = value,
                executeWithCommit = true
            )
        }

    private var refreshToken: String?
        get() = get(REFRESH_TOKEN)
        set(value) = if (value == null) {
            remove(
                key = REFRESH_TOKEN,
                executeWithCommit = true
            )
        } else {
            set(
                key = REFRESH_TOKEN,
                value = value,
                executeWithCommit = true
            )
        }

    var authStatus: AuthStatus
        get() {
            return if (
                accessToken.isNullOrEmpty()
                && tokenType.isNullOrEmpty()
                && expiresIn == null
                && refreshToken.isNullOrEmpty()
            ) {
                AuthStatus.Anonymous
            } else {
                AuthStatus.Authenticated(
                    accessToken = accessToken.orEmpty(),
                    tokenType = tokenType,
                    expiresIn = expiresIn,
                    refreshToken = refreshToken.orEmpty(),
                )
            }
        }
        set(value) {
            if (value is AuthStatus.Authenticated) {
                accessToken = value.accessToken
                tokenType = value.tokenType
                expiresIn = value.expiresIn
                refreshToken = value.refreshToken
            } else {
                // AuthStatus.Anonymous means logging out
                clearAll(executeWithCommit = true)
            }
        }
}
