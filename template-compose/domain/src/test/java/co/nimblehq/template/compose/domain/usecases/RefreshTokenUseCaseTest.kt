package co.nimblehq.template.compose.domain.usecases

import co.nimblehq.template.compose.domain.repositories.TokenRepository
import co.nimblehq.template.compose.domain.test.MockUtil
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RefreshTokenUseCaseTest {

    private val mockRepository: TokenRepository = mockk()
    private lateinit var useCase: RefreshTokenUseCase

    @Before
    fun setUp() {
        useCase = RefreshTokenUseCase(mockRepository)
    }

    @Test
    fun `When calling request successfully, it returns success response`() = runTest {
        val expected = MockUtil.oAuthTokenModel
        every { mockRepository.refreshToken(any()) } returns flowOf(expected)

        useCase(
            refreshToken = "refreshToken"
        ).collect {
            it shouldBe expected
        }
    }

    @Test
    fun `When calling request fails, it returns wrapped error`() = runTest {
        val expected = Exception()
        every { mockRepository.refreshToken(any()) } returns flow { throw expected }

        useCase(
            refreshToken = "refreshToken"
        ).catch {
            it shouldBe expected
        }.collect {}
    }
}
