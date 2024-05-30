package co.nimblehq.template.compose.data.repositories

import co.nimblehq.template.compose.data.extensions.flowTransform
import co.nimblehq.template.compose.data.remote.models.requests.toRefreshTokenRequest
import co.nimblehq.template.compose.data.remote.models.responses.toModel
import co.nimblehq.template.compose.data.remote.services.ApiService
import co.nimblehq.template.compose.domain.models.OAuthTokenModel
import co.nimblehq.template.compose.domain.repositories.TokenRepository
import kotlinx.coroutines.flow.Flow
import java.util.Properties

const val CLIENT_ID_KEY = "CLIENT_ID"
const val CLIENT_SECRET_KEY = "CLIENT_SECRET"

class TokenRepositoryImpl constructor(
    private val apiService: ApiService,
    private val apiConfigProperties: Properties,
) : TokenRepository {

    override fun refreshToken(refreshToken: String): Flow<OAuthTokenModel> =
        flowTransform {
            apiService.refreshOAuthToken(
                clientId = apiConfigProperties.getProperty(CLIENT_ID_KEY),
                clientSecret = apiConfigProperties.getProperty(CLIENT_SECRET_KEY),
                body = refreshToken.toRefreshTokenRequest()
            ).toModel()
        }
}
