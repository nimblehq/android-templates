package co.nimblehq.template.xml.data.repository

import co.nimblehq.template.xml.data.extensions.flowTransform
import co.nimblehq.template.xml.data.response.AuthenticateResponse
import co.nimblehq.template.xml.data.service.AuthService
import co.nimblehq.template.xml.data.service.authenticator.TokenRefresher
import kotlinx.coroutines.flow.Flow

class TokenRefresherImpl constructor(
    private val authService: AuthService
) : TokenRefresher {

    override suspend fun refreshToken(): Flow<AuthenticateResponse> = flowTransform {
        authService.refreshToken()
    }
}
