package co.nimblehq.sample.xml.domain.usecase

import co.nimblehq.sample.xml.domain.model.Model
import co.nimblehq.sample.xml.domain.repository.Repository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetModelsUseCaseTest {

    private lateinit var mockRepository: Repository
    private lateinit var getModelsUseCase: GetModelsUseCase

    private val model = Model(id = 1)

    @Before
    fun setup() {
        mockRepository = mockk()
        getModelsUseCase = GetModelsUseCase(mockRepository)
    }

    @Test
    fun `When request successful, it returns success`() = runTest {
        val expected = listOf(model)
        every { mockRepository.getModels() } returns flowOf(expected)

        getModelsUseCase().collect {
            it shouldBe expected
        }
    }

    @Test
    fun `When request failed, it returns error`() = runTest {
        val expected = Exception()
        every { mockRepository.getModels() } returns flow { throw expected }

        getModelsUseCase().catch {
            it shouldBe expected
        }.collect()
    }
}
