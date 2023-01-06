package co.nimblehq.template.xml.data.service

import co.nimblehq.template.xml.data.response.AuthenticateResponse
import retrofit2.http.POST

interface AuthService {

    @POST("refreshToken")
    suspend fun refreshToken(): AuthenticateResponse
}
