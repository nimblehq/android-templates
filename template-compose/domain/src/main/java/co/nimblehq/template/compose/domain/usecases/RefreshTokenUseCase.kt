package co.nimblehq.template.compose.domain.usecases

import co.nimblehq.template.compose.domain.models.OAuthTokenModel
import co.nimblehq.template.compose.domain.repositories.TokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val repository: TokenRepository
) {

    operator fun invoke(refreshToken: String): Flow<OAuthTokenModel> {
        return repository.refreshToken(refreshToken)
    }
}
