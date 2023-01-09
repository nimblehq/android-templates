package co.nimblehq.sample.compose.ui.screens.home

import app.cash.turbine.test
import co.nimblehq.sample.compose.domain.model.Model
import co.nimblehq.sample.compose.domain.usecase.UseCase
import co.nimblehq.sample.compose.model.toUiModel
import co.nimblehq.sample.compose.test.CoroutineTestRule
import co.nimblehq.sample.compose.ui.AppDestination
import co.nimblehq.sample.compose.util.DispatchersProvider
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutineTestRule()

    private val mockUseCase: UseCase = mockk()

    private lateinit var viewModel: HomeViewModel

    private val models = listOf(Model(1), Model(2), Model(3))

    @Before
    fun setUp() {
        every { mockUseCase() } returns flowOf(models)

        initViewModel()
    }

    @Test
    fun `when loading models successfully, it shows the model list`() = runTest {
        viewModel.uiModels.test {
            expectMostRecentItem() shouldBe models.map { it.toUiModel() }
        }
    }

    @Test
    fun `when loading models failed, it shows the corresponding error`() = runTest {
        val error = Exception()
        every { mockUseCase() } returns flow { throw error }
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        viewModel.error.test {
            advanceUntilIdle()

            expectMostRecentItem() shouldBe error
        }
    }

    @Test
    fun `When loading models, it shows and hides loading correctly`() = runTest {
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        viewModel.showLoading.test {
            awaitItem() shouldBe false
            awaitItem() shouldBe true
            awaitItem() shouldBe false
        }
    }

    @Test
    fun `when calling navigate to Second, it navigates to Second screen`() = runTest {
        viewModel.navigator.test {
            viewModel.navigateToSecond(models[0].toUiModel())

            expectMostRecentItem() shouldBe AppDestination.Second
        }
    }

    private fun initViewModel(dispatchers: DispatchersProvider = coroutinesRule.testDispatcherProvider) {
        viewModel = HomeViewModel(
            mockUseCase,
            dispatchers
        )
    }
}
