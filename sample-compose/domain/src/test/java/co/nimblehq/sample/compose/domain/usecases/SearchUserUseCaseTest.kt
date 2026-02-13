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
class SearchUserUseCaseTest {

    private lateinit var mockRepository: Repository
    private lateinit var searchUserUseCase: SearchUserUseCase

    @Before
    fun setUp() {
        mockRepository = mockk()
        searchUserUseCase = SearchUserUseCase(mockRepository)
    }

    @Test
    fun `When request successful, it returns success`() = runTest {
        val expected = MockUtil.models
        val username = "name1"
        every { mockRepository.searchUser(username) } returns flowOf(expected)

        searchUserUseCase(username).collect {
            it shouldBe expected
        }
    }

    @Test
    fun `When request failed, it returns error`() = runTest {
        val expected = Exception()
        val username = "name1"
        every { mockRepository.searchUser(username) } returns flow { throw expected }

        searchUserUseCase(username).catch {
            it shouldBe expected
        }.collect()
    }
}
