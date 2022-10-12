package co.nimblehq.sample.compose.domain.usecase

import co.nimblehq.sample.compose.domain.model.Model
import co.nimblehq.sample.compose.domain.repository.Repository
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UseCaseTest {

    private lateinit var mockRepository: Repository
    private lateinit var useCase: UseCase

    private val model = Model(id = 1)

    @Before
    fun setup() {
        mockRepository = mockk()
        useCase = UseCase(mockRepository)
    }

    @Test
    fun `When request successful, it returns success`() = runBlockingTest {
        val expected = listOf(model)
        every { mockRepository.getModels() } returns flowOf(expected)

        useCase().collect {
            it shouldBe expected
        }
    }

    @Test
    fun `When request failed, it returns error`() = runBlockingTest {
        val expected = Exception()
        every { mockRepository.getModels() } returns flow { throw expected }

        useCase().catch {
            it shouldBe expected
        }.collect()
    }
}
