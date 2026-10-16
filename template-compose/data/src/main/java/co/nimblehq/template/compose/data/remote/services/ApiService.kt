package co.nimblehq.template.compose.data.remote.services

import co.nimblehq.template.compose.data.remote.models.requests.RefreshTokenRequest
import co.nimblehq.template.compose.data.remote.models.responses.OAuthTokenResponse
import co.nimblehq.template.compose.data.remote.models.responses.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @GET("users")
    suspend fun getResponses(): List<Response>

    // TODO This is just an example. Refactor to a real implementation.
    @POST("oauth/token")
    suspend fun refreshOAuthToken(
        @Header("Client-Id") clientId: String,
        @Header("Client-Secret") clientSecret: String,
        @Body body: RefreshTokenRequest,
    ): OAuthTokenResponse
}
