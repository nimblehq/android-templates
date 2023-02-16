package co.nimblehq.sample.compose.domain.usecase

import co.nimblehq.sample.compose.domain.repository.AppPreferencesRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class IsFirstTimeLaunchPreferencesUseCaseTest {

    private lateinit var mockAppPreferencesRepository: AppPreferencesRepository
    private lateinit var isFirstTimeLaunchPreferencesUseCase : IsFirstTimeLaunchPreferencesUseCase

    @Before
    fun setUp() {
        mockAppPreferencesRepository = mockk()
        isFirstTimeLaunchPreferencesUseCase = IsFirstTimeLaunchPreferencesUseCase(mockAppPreferencesRepository)
    }

    @Test
    fun `When getting isFirstTimeLaunch successful, it returns the correct value`() = runTest {
        every { mockAppPreferencesRepository.isFirstTimeLaunch() } returns flowOf(true)

        isFirstTimeLaunchPreferencesUseCase().collect {
            it shouldBe true
        }
    }

    @Test
    fun `When getting isFirstTimeLaunch but it failed, it returns the Exception`() = runTest {
        val expected = Exception()
        every { mockAppPreferencesRepository.isFirstTimeLaunch() } returns flow { throw Exception() }

        isFirstTimeLaunchPreferencesUseCase().catch {
            it shouldBe expected
        }.collect()
    }
}
