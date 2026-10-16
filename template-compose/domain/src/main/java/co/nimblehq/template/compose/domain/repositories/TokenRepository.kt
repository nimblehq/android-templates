package co.nimblehq.template.compose.domain.repositories

import co.nimblehq.template.compose.domain.models.OAuthTokenModel
import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    fun refreshToken(refreshToken: String): Flow<OAuthTokenModel>
}
