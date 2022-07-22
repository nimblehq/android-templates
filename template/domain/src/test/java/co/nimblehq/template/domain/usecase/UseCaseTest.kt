package co.nimblehq.template.domain.usecase

import co.nimblehq.template.domain.model.Model
import co.nimblehq.template.domain.repository.Repository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        coEvery { mockRepository.getModels() } returns expected

        useCase.execute().run {
            (this as UseCaseResult.Success).data shouldBe expected
        }
    }

    @Test
    fun `When request failed, it returns error`() = runBlockingTest {
        val expected = IllegalStateException()
        coEvery { mockRepository.getModels() } throws expected

        useCase.execute().run {
            (this as UseCaseResult.Error).exception shouldBe expected
        }
    }
}
