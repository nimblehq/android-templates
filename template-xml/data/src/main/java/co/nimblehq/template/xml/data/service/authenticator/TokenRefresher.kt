package co.nimblehq.template.xml.data.service.authenticator

import co.nimblehq.template.xml.data.response.AuthenticateResponse
import kotlinx.coroutines.flow.Flow

interface TokenRefresher {

    suspend fun refreshToken(): Flow<AuthenticateResponse>
}
