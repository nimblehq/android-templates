package co.nimblehq.sample.xml.domain.usecase

import co.nimblehq.sample.xml.domain.repository.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFirstTimeLaunchPreferencesUseCase @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) {

    operator fun invoke(): Flow<Boolean> {
        return appPreferencesRepository.isFirstTimeLaunch()
    }
}
