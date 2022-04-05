package co.nimblehq.coroutine.data.repositoryimpl

import co.nimblehq.coroutine.data.response.UserResponse
import co.nimblehq.coroutine.data.response.toUsers
import co.nimblehq.coroutine.data.service.ApiService
import co.nimblehq.coroutine.domain.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    private lateinit var mockService: ApiService
    private lateinit var repository: UserRepository

    private val userResponse = UserResponse(
        id = 1,
        name = "name",
        username = "username",
        email = "email",
        addressResponse = null,
        phone = null,
        website = null
    )

    @Before
    fun setup() {
        mockService = mockk()
        repository = UserRepositoryImpl(mockService)
    }

    @Test
    fun `When calling getUsers request successfully, it returns success response`() = runBlockingTest {
        coEvery { mockService.getUsers() } returns listOf(userResponse)

        repository.getUsers() shouldBe listOf(userResponse).toUsers()
    }

    @Test
    fun `When calling getUsers request failed, it returns wrapped error`() = runBlockingTest {
        coEvery { mockService.getUsers() } throws Throwable()

        shouldThrow<Throwable> {
            repository.getUsers()
        }
    }
}
