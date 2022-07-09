package co.nimblehq.coroutine.domain.usecase

import co.nimblehq.coroutine.domain.model.Model
import co.nimblehq.coroutine.domain.repository.Repository
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
    private lateinit var usecase: UseCase

    private val model = Model(id = 1)

    @Before
    fun setup() {
        mockRepository = mockk()
        usecase = UseCase(mockRepository)
    }

    @Test
    fun `When request successful, it returns success`() = runBlockingTest {
        val expected = listOf(model)
        coEvery { mockRepository.getModels() } returns expected

        usecase.execute().run {
            (this as UseCaseResult.Success).data shouldBe expected
        }
    }

    @Test
    fun `When request failed, it returns error`() = runBlockingTest {
        val expected = IllegalStateException()
        coEvery { mockRepository.getModels() } throws expected

        usecase.execute().run {
            (this as UseCaseResult.Error).exception shouldBe expected
        }
    }
}
