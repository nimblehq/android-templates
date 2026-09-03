package co.nimblehq.template.compose.data.repositories

import co.nimblehq.template.compose.data.extensions.flowTransform
import co.nimblehq.template.compose.data.local.preferences.NetworkEncryptedSharedPreferences
import co.nimblehq.template.compose.domain.models.AuthStatus
import co.nimblehq.template.compose.domain.repositories.AuthPreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthPreferenceRepositoryImpl @Inject constructor(
    private val networkEncryptedPreferences: NetworkEncryptedSharedPreferences,
) : AuthPreferenceRepository {

    override fun updateAuthenticationStatus(authStatus: AuthStatus): Flow<Unit> = flowTransform {
        networkEncryptedPreferences.authStatus = authStatus
    }

    override fun getAuthStatus(): Flow<AuthStatus> = flowTransform {
        networkEncryptedPreferences.authStatus
    }
}
