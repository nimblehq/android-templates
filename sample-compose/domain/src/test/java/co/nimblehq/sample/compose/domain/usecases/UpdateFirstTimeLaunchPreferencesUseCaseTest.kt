package co.nimblehq.sample.compose.domain.usecases

import co.nimblehq.sample.compose.domain.repositories.AppPreferencesRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateFirstTimeLaunchPreferencesUseCaseTest {

    private lateinit var mockAppPreferencesRepository: AppPreferencesRepository
    private lateinit var updateFirstTimeLaunchPreferencesUseCase: UpdateFirstTimeLaunchPreferencesUseCase

    @Before
    fun setUp() {
        mockAppPreferencesRepository = mockk()
        updateFirstTimeLaunchPreferencesUseCase =
            UpdateFirstTimeLaunchPreferencesUseCase(mockAppPreferencesRepository)
    }

    @Test
    fun `When updating isFirstTimeLaunch, it calls updateFirstTimeLaunch from AppPreferencesRepository`() =
        runTest {
            coEvery { mockAppPreferencesRepository.updateFirstTimeLaunch(true) } returns Unit

            updateFirstTimeLaunchPreferencesUseCase(true)

            coVerify(exactly = 1) { mockAppPreferencesRepository.updateFirstTimeLaunch(true) }
        }
}
