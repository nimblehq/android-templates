package co.nimblehq.template.xml.data.service

import co.nimblehq.template.xml.data.response.AuthenticateResponse

interface SessionManager {

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun refresh(authenticateResponse: AuthenticateResponse)
}
