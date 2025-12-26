package co.nimblehq.sample.compose.domain.usecases

import co.nimblehq.sample.compose.domain.repositories.Repository
import co.nimblehq.sample.compose.domain.test.MockUtil
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class GetDetailsUseCaseTest {

    private lateinit var mockRepository: Repository
    private lateinit var getDetailsUseCase: GetDetailsUseCase

    @Before
    fun setUp() {
        mockRepository = mockk()
        getDetailsUseCase = GetDetailsUseCase(mockRepository)
    }

    @Test
    fun `When request successful, it returns success`() = runTest {
        val expected = MockUtil.models.first()
        every { mockRepository.getDetails(any()) } returns flowOf(expected)

        getDetailsUseCase(1).collect {
            it shouldBe expected
        }
    }

    @Test
    fun `When request failed, it returns error`() = runTest {
        val expected = Exception()
        every { mockRepository.getDetails(any()) } returns flow { throw expected }

        getDetailsUseCase(1).catch {
            it shouldBe expected
        }.collect()
    }
}
