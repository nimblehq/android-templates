package co.nimblehq.template.compose.domain.usecases

import co.nimblehq.template.compose.domain.models.toAuthenticatedStatus
import co.nimblehq.template.compose.domain.repositories.AuthPreferenceRepository
import co.nimblehq.template.compose.domain.test.MockUtil
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpdateLoginTokensUseCaseTest {

    private lateinit var mockAuthPreferenceRepository: AuthPreferenceRepository
    private lateinit var updateLoginTokensUseCase: UpdateLoginTokensUseCase

    @Before
    fun setUp() {
        mockAuthPreferenceRepository = mockk()
        updateLoginTokensUseCase = UpdateLoginTokensUseCase(mockAuthPreferenceRepository)
    }

    @Test
    fun `When updating login credentials, it calls updateLoginCredentials from AuthPreferenceRepository`() =
        runTest {
            every {
                mockAuthPreferenceRepository.updateAuthenticationStatus(any())
            } returns flowOf(Unit)

            updateLoginTokensUseCase(MockUtil.oAuthTokenModel.toAuthenticatedStatus())

            verify(exactly = 1) {
                mockAuthPreferenceRepository.updateAuthenticationStatus(any())
            }
        }
}
