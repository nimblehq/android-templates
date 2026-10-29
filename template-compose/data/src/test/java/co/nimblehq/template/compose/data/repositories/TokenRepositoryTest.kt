package co.nimblehq.template.compose.data.repositories

import app.cash.turbine.test
import co.nimblehq.template.compose.data.remote.models.responses.toModel
import co.nimblehq.template.compose.data.remote.services.ApiService
import co.nimblehq.template.compose.data.test.MockUtil
import co.nimblehq.template.compose.domain.repositories.TokenRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.util.Properties

class TokenRepositoryTest {

    private val mockService: ApiService = mockk()
    private val mockApiConfigProperties: Properties = mockk()
    private lateinit var repository: TokenRepository

    @Before
    fun setUp() {
        repository = TokenRepositoryImpl(
            apiService = mockService,
            apiConfigProperties = mockApiConfigProperties
        )
        every { mockApiConfigProperties.getProperty(eq(CLIENT_ID_KEY)) } returns "CLIENT_ID"
        every { mockApiConfigProperties.getProperty(eq(CLIENT_SECRET_KEY)) } returns "CLIENT_SECRET"
    }

    @Test
    fun `When calling refreshToken request succeeds, it returns success response`() = runTest {
        val expected = MockUtil.oauthTokenResponse

        coEvery { mockService.refreshOAuthToken(any(), any(), any()) } returns expected

        repository.refreshToken(
            refreshToken = "refreshToken"
        ).test {
            awaitItem() shouldBe expected.toModel()
            awaitComplete()
        }
    }

    @Test
    fun `When calling refreshToken request fails, it returns wrapped error`() = runTest {
        val expected = Throwable()
        coEvery { mockService.refreshOAuthToken(any(), any(), any()) } throws expected

        repository.refreshToken(
            refreshToken = "refreshToken"
        ).test {
            awaitError() shouldBe expected
        }
    }
}
