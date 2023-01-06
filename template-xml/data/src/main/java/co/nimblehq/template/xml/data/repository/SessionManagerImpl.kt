package co.nimblehq.template.xml.data.repository

import co.nimblehq.template.xml.data.response.AuthenticateResponse
import co.nimblehq.template.xml.data.service.SessionManager

class SessionManagerImpl: SessionManager {
    
    override suspend fun getAccessToken(): String {
        TODO("Not yet implemented")
    }

    override suspend fun getRefreshToken(): String {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(authenticateResponse: AuthenticateResponse) {
        TODO("Not yet implemented")
    }
}
