package co.nimblehq.template.compose.data.remote

import co.nimblehq.template.compose.data.remote.authenticator.RequestAuthenticator
import co.nimblehq.template.compose.data.remote.interceptor.AUTH_HEADER
import co.nimblehq.template.compose.data.test.CoroutineTestRule
import co.nimblehq.template.compose.data.test.MockUtil
import co.nimblehq.template.compose.domain.exceptions.NoConnectivityException
import co.nimblehq.template.compose.domain.models.toAuthenticatedStatus
import co.nimblehq.template.compose.domain.usecases.GetAuthStatusUseCase
import co.nimblehq.template.compose.domain.usecases.RefreshTokenUseCase
import co.nimblehq.template.compose.domain.usecases.UpdateLoginTokensUseCase
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RequestAuthenticatorTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val mockGetAuthStatusUseCase: GetAuthStatusUseCase = mockk()
    private val mockRefreshTokenUseCase: RefreshTokenUseCase = mockk()
    private val mockUpdateLoginTokensUseCase: UpdateLoginTokensUseCase = mockk()
    private val mockRoute: Route = mockk()
    private val mockResponse: Response = mockk(relaxed = true)

    private lateinit var authenticator: RequestAuthenticator

    @Before
    fun setUp() {
        authenticator = RequestAuthenticator(
            getAuthStatusUseCase = mockGetAuthStatusUseCase,
            refreshTokenUseCase = mockRefreshTokenUseCase,
            updateLoginTokensUseCase = mockUpdateLoginTokensUseCase,
            dispatchersProvider = coroutineRule.testDispatcherProvider,
        )
        every { mockGetAuthStatusUseCase() } returns flowOf(MockUtil.authenticatedStatus)
        every { mockRefreshTokenUseCase(any()) } returns flowOf(MockUtil.oAuthTokenModel)
        every { mockUpdateLoginTokensUseCase(any()) } returns flowOf(Unit)
        every { mockResponse.request.header(any()) } returns "${MockUtil.authenticatedStatus.tokenType} ${MockUtil.authenticatedStatus.accessToken}"
    }

    @Test
    fun `When refresh access token fails with no internet, it returns null`() {
        every { mockRefreshTokenUseCase(any()) } returns flow { throw NoConnectivityException }

        authenticator.authenticate(route = mockRoute, response = mockResponse) shouldBe null
    }

    @Test
    fun `When refresh access token fails with other error, it returns the original request`() {
        val error = Exception()
        every { mockRefreshTokenUseCase(any()) } returns flow { throw error }

        authenticator.authenticate(route = mockRoute, response = mockResponse) shouldBe mockResponse.request
    }

    @Test
    fun `When refresh access token fails for three times, it returns null`() {
        val error = Exception()
        every { mockRefreshTokenUseCase(any()) } returns flow { throw error }

        authenticator.authenticate(route = mockRoute, response = mockResponse) is Request
        authenticator.authenticate(route = mockRoute, response = mockResponse) is Request
        authenticator.authenticate(route = mockRoute, response = mockResponse) is Request
        authenticator.authenticate(route = mockRoute, response = mockResponse) shouldBe null
    }

    @Test
    fun `When new access token is null, it returns null`() {
        every { mockRefreshTokenUseCase(any()) } returns flowOf(MockUtil.oAuthTokenModel.copy(accessToken = null))

        authenticator.authenticate(route = mockRoute, response = mockResponse) shouldBe null
    }

    @Test
    fun `When new access token is the same as failed access token, it returns null`() {
        authenticator.authenticate(route = mockRoute, response = mockResponse) shouldBe null
    }

    @Test
    fun `When new access token is not null and not equals to old access token, it returns request with new access token`() {
        val expiredToken = "${MockUtil.oAuthTokenModel.tokenType} ${MockUtil.oAuthTokenModel.accessToken}"
        val newToken = MockUtil.oAuthTokenModel.copy(accessToken = "newAccessToken")
        val expected = "${newToken.tokenType} ${newToken.accessToken}"
        every { mockRefreshTokenUseCase(any()) } returns flowOf(newToken)
        every { mockResponse.request.header(any()) } returnsMany listOf(expiredToken, expected)
        every { mockResponse.request.newBuilder().header(any(), any()).build() } returns mockResponse.request

        authenticator.authenticate(route = mockRoute, response = mockResponse)?.header(AUTH_HEADER) shouldBe expected

        verify(exactly = 1) {
            mockUpdateLoginTokensUseCase(newToken.toAuthenticatedStatus())
        }
    }
}
