package co.nimblehq.template.compose.data.remote

import co.nimblehq.template.compose.data.remote.authenticator.RequestAuthenticator
import co.nimblehq.template.compose.data.remote.interceptor.AUTH_HEADER
import co.nimblehq.template.compose.data.test.CoroutineTestRule
import co.nimblehq.template.compose.data.test.MockUtil
import co.nimblehq.template.compose.domain.exceptions.NoConnectivityException
import co.nimblehq.template.compose.domain.models.AuthStatus
import co.nimblehq.template.compose.domain.models.toAuthenticatedStatus
import co.nimblehq.template.compose.domain.usecases.GetAuthStatusUseCase
import co.nimblehq.template.compose.domain.usecases.RefreshTokenUseCase
import co.nimblehq.template.compose.domain.usecases.UpdateLoginTokensUseCase
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
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
        every { mockResponse.priorResponse } returns null
    }

    @Test
    fun `When refresh access token fails with no internet, it returns null`() {
        every { mockRefreshTokenUseCase(any()) } returns flow { throw NoConnectivityException }

        authenticator.authenticate(route = mockRoute, response = mockResponse) shouldBe null
    }

    @Test
    fun `When refresh access token fails with other error, it clears the tokens and returns null`() {
        val error = Exception()
        every { mockRefreshTokenUseCase(any()) } returns flow { throw error }

        authenticator.authenticate(route = mockRoute, response = mockResponse) shouldBe null

        verify(exactly = 1) {
            mockUpdateLoginTokensUseCase(AuthStatus.Anonymous)
        }
    }

    @Test
    fun `When the request was already retried three times, it returns null`() {
        authenticator.authenticate(route = mockRoute, response = responseWithPriorCount(3)) shouldBe null

        verify(exactly = 0) {
            mockRefreshTokenUseCase(any())
        }
    }

    @Test
    fun `When the request was retried fewer than three times, it retries with the existing token`() {
        val existingToken = "${MockUtil.authenticatedStatus.tokenType} ${MockUtil.authenticatedStatus.accessToken}"
        every { mockResponse.request.header(any()) } returnsMany listOf("tokenType oldAccessToken", existingToken)
        every { mockResponse.request.newBuilder().header(any(), any()).build() } returns mockResponse.request

        val result = authenticator.authenticate(route = mockRoute, response = responseWithPriorCount(2))

        result.shouldBeInstanceOf<Request>()
        result.header(AUTH_HEADER) shouldBe existingToken
    }

    @Test
    fun `When the refresh response omits the refresh token, it keeps the existing refresh token`() {
        val newToken = MockUtil.oAuthTokenModel.copy(accessToken = "newAccessToken", refreshToken = null)
        every { mockRefreshTokenUseCase(any()) } returns flowOf(newToken)
        every { mockResponse.request.newBuilder().header(any(), any()).build() } returns mockResponse.request

        authenticator.authenticate(route = mockRoute, response = mockResponse)

        verify(exactly = 1) {
            mockUpdateLoginTokensUseCase(
                newToken.toAuthenticatedStatus().copy(refreshToken = MockUtil.authenticatedStatus.refreshToken)
            )
        }
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

    private fun responseWithPriorCount(count: Int): Response =
        (0 until count).fold(mockResponse) { prior, _ ->
            mockk<Response>(relaxed = true) {
                every { request } returns mockResponse.request
                every { priorResponse } returns prior
            }
        }
}
