package co.nimblehq.template.compose.domain.usecases

import co.nimblehq.template.compose.domain.models.AuthStatus
import co.nimblehq.template.compose.domain.repositories.AuthPreferenceRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetAuthStatusUseCaseTest {

    private lateinit var mockAuthPreferenceRepository: AuthPreferenceRepository
    private lateinit var getAuthStatusUseCase: GetAuthStatusUseCase

    @Before
    fun setUp() {
        mockAuthPreferenceRepository = mockk()
        getAuthStatusUseCase = GetAuthStatusUseCase(mockAuthPreferenceRepository)
    }

    @Test
    fun `When calling request after logging in, it returns Authenticated status`() =
        runTest {
            val expected = AuthStatus.Authenticated(
                accessToken = "accessToken",
                tokenType = "tokenType",
                expiresIn = 1,
                refreshToken = "refreshToken",
            )
            every {
                mockAuthPreferenceRepository.getAuthStatus()
            } returns flowOf(expected)

            getAuthStatusUseCase().collect {
                it shouldBe expected
            }
        }

    @Test
    fun `When calling request before logging in, it returns Anonymous status`() =
        runTest {
            val expected = AuthStatus.Anonymous
            every {
                mockAuthPreferenceRepository.getAuthStatus()
            } returns flowOf(expected)

            getAuthStatusUseCase().collect {
                it shouldBe expected
            }
        }
}
