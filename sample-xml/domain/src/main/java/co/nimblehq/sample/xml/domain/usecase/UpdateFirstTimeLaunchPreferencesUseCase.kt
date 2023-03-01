package co.nimblehq.sample.xml.domain.usecase

import co.nimblehq.sample.xml.domain.repository.AppPreferencesRepository
import javax.inject.Inject

class UpdateFirstTimeLaunchPreferencesUseCase @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) {

    suspend operator fun invoke(isFirstTimeLaunch: Boolean) {
        appPreferencesRepository.updateFirstTimeLaunch(isFirstTimeLaunch)
    }
}
