package co.nimblehq.sample.compose.domain.usecase

import co.nimblehq.sample.compose.domain.repository.AppPreferencesRepository
import javax.inject.Inject

class UpdateFirstTimeLaunchPreferencesUseCase @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) {

    suspend operator fun invoke(isFirstTimeLaunch: Boolean) {
        appPreferencesRepository.updateFirstTimeLaunch(isFirstTimeLaunch)
    }
}
