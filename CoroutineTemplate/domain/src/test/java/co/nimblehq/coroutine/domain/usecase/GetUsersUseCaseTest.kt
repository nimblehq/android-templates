package co.nimblehq.coroutine.domain.usecase

import co.nimblehq.coroutine.domain.model.User
import co.nimblehq.coroutine.domain.repository.UserRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUsersUseCaseTest {

    private lateinit var mockRepository: UserRepository
    private lateinit var usecase: GetUsersUseCase

    private val user = User(
        id = 1,
        name = "name",
        username = "username",
        email = "email",
        address = null,
        phone = "",
        website = ""
    )

    @Before
    fun setup() {
        mockRepository = mockk()
        usecase = GetUsersUseCase(mockRepository)
    }

    @Test
    fun `When calling request successfully, it returns success response`() = runBlockingTest {
        val expected = listOf(user)
        coEvery { mockRepository.getUsers() } returns expected

        usecase.execute().run {
            (this as UseCaseResult.Success).data shouldBe expected
        }
    }

    @Test
    fun `When calling request failed, it returns wrapped error`() = runBlockingTest {
        val expected = Exception()
        coEvery { mockRepository.getUsers() } throws expected

        usecase.execute().run {
            (this as UseCaseResult.Error).exception shouldBe expected
        }
    }
}
