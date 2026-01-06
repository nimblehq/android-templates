package co.nimblehq.sample.compose.data.repositories

import co.nimblehq.sample.compose.data.remote.models.responses.toModel
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

    @Test
    fun `When searching user successful, it returns success`() = runTest {
        val expected = MockUtil.responses
        coEvery { mockService.searchUser(any()) } returns expected

        repository.searchUser("John").collect {
            it shouldBe expected.toModels()
        }
    }

    @Test
    fun `When searching user failed, it returns error`() = runTest {
        val expected = Throwable()
        coEvery { mockService.searchUser(any()) } throws expected

        repository.searchUser("John").catch {
            it shouldBe expected
        }.collect()
    }

    @Test
    fun `When getting details successful, it returns success`() = runTest {
        val expected = MockUtil.responses.first()
        coEvery { mockService.getDetails(any()) } returns expected

        repository.getDetails(1).collect {
            it shouldBe expected.toModel()
        }
    }

    @Test
    fun `When getting details failed, it returns error`() = runTest {
        val expected = Throwable()
        coEvery { mockService.getDetails(any()) } throws expected

        repository.getDetails(1).catch {
            it shouldBe expected
        }.collect()
    }
}
