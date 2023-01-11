package co.nimblehq.sample.compose.domain.usecase

import co.nimblehq.sample.compose.domain.repository.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFirstTimeLaunchPreferencesUseCase @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) {

    operator fun invoke(): Flow<Boolean> {
        return appPreferencesRepository.getFirstTimeLaunchPreferences()
    }
}
