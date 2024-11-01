package co.nimblehq.template.compose.data.repositories

import co.nimblehq.template.compose.data.local.preferences.NetworkEncryptedSharedPreferences
import co.nimblehq.template.compose.data.remote.models.responses.toModel
import co.nimblehq.template.compose.data.test.MockUtil
import co.nimblehq.template.compose.domain.models.AuthStatus
import co.nimblehq.template.compose.domain.models.toAuthenticatedStatus
import co.nimblehq.template.compose.domain.repositories.AuthPreferenceRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AuthPreferenceRepositoryTest {

    private lateinit var repository: AuthPreferenceRepository

    private val mockNetworkSharedPreferences: NetworkEncryptedSharedPreferences = mockk()

    @Before
    fun setUp() {
        repository = AuthPreferenceRepositoryImpl(mockNetworkSharedPreferences)
        every { mockNetworkSharedPreferences.set<Any>(any(), any()) } returns Unit
        every { mockNetworkSharedPreferences.authStatus = any() } returns Unit
    }

    @Test
    fun `When calling updateAuthenticationStatus, it updates preference`() = runTest {
        val expected = MockUtil.oauthTokenResponse.toModel().toAuthenticatedStatus()
        repository.updateAuthenticationStatus(expected).collect()

        verify {
            mockNetworkSharedPreferences.authStatus = expected
        }
    }

    @Test
    fun `When calling getAuthStatus, it returns Authenticated if there is AccessToken`() = runTest {
        val expected = MockUtil.oauthTokenResponse.toModel().toAuthenticatedStatus()
        every { mockNetworkSharedPreferences.authStatus } returns expected

        repository.getAuthStatus().collect {
            it shouldBe expected
        }
    }

    @Test
    fun `When calling getAuthStatus, it returns Anonymous status if there is no AccessToken`() =
        runTest {
            val expected = AuthStatus.Anonymous
            every { mockNetworkSharedPreferences.authStatus } returns expected

            repository.getAuthStatus().collect {
                it shouldBe expected
            }
        }

    @Test
    fun `When calling getAuthStatus, it returns Anonymous status if the AccessToken is empty`() =
        runTest {
            val expected = AuthStatus.Anonymous
            every { mockNetworkSharedPreferences.authStatus } returns expected

            repository.getAuthStatus().collect {
                it shouldBe expected
            }
        }
}
