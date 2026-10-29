package co.nimblehq.template.compose.domain.repositories

import co.nimblehq.template.compose.domain.models.AuthStatus
import kotlinx.coroutines.flow.Flow

interface AuthPreferenceRepository {

    fun updateAuthenticationStatus(authStatus: AuthStatus): Flow<Unit>

    fun getAuthStatus(): Flow<AuthStatus>
}
