package co.nimblehq.template.compose.domain.usecases

import co.nimblehq.template.compose.domain.models.AuthStatus
import co.nimblehq.template.compose.domain.repositories.AuthPreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthStatusUseCase @Inject constructor(
    private val authPreferenceRepository: AuthPreferenceRepository
) {

    operator fun invoke(): Flow<AuthStatus> = authPreferenceRepository.getAuthStatus()
}
