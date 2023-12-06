package co.nimblehq.sample.compose.domain.usecases

import co.nimblehq.sample.compose.domain.repositories.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFirstTimeLaunchPreferencesUseCase @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) {

    operator fun invoke(): Flow<Boolean> {
        return appPreferencesRepository.isFirstTimeLaunch()
    }
}
