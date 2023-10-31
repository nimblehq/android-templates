package co.nimblehq.sample.compose.data.repositories

import co.nimblehq.sample.compose.data.remote.models.responses.toModels
import co.nimblehq.sample.compose.data.remote.services.ApiService
import co.nimblehq.sample.compose.data.test.MockUtil
import co.nimblehq.sample.compose.domain.repositories.Repository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryTest {

    private lateinit var mockService: ApiService
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        mockService = mockk()
        repository = RepositoryImpl(mockService)
    }

    @Test
    fun `When request successful, it returns success`() = runTest {
        val expected = MockUtil.responses
        coEvery { mockService.getResponses() } returns expected

        repository.getModels().collect {
            it shouldBe expected.toModels()
        }
    }

    @Test
    fun `When request failed, it returns error`() = runTest {
        val expected = Throwable()
        coEvery { mockService.getResponses() } throws expected

        repository.getModels().catch {
            it shouldBe expected
        }.collect()
    }
}
