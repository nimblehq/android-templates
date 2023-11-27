package co.nimblehq.sample.compose.domain.usecases

import co.nimblehq.sample.compose.domain.repositories.AppPreferencesRepository
import javax.inject.Inject

class UpdateFirstTimeLaunchPreferencesUseCase @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) {

    suspend operator fun invoke(isFirstTimeLaunch: Boolean) {
        appPreferencesRepository.updateFirstTimeLaunch(isFirstTimeLaunch)
    }
}
