package co.nimblehq.sample.compose.ui.screens.home

import app.cash.turbine.test
import co.nimblehq.sample.compose.domain.model.Model
import co.nimblehq.sample.compose.domain.usecase.*
import co.nimblehq.sample.compose.model.toUiModel
import co.nimblehq.sample.compose.test.CoroutineTestRule
import co.nimblehq.sample.compose.ui.AppDestination
import co.nimblehq.sample.compose.util.DispatchersProvider
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutineTestRule()

    private val mockGetModelsUseCase: GetModelsUseCase = mockk()
    private val mockIsFirstTimeLaunchPreferencesUseCase: IsFirstTimeLaunchPreferencesUseCase = mockk()
    private val mockUpdateFirstTimeLaunchPreferencesUseCase: UpdateFirstTimeLaunchPreferencesUseCase = mockk()

    private lateinit var viewModel: HomeViewModel

    private val models = listOf(Model(1), Model(2), Model(3))

    @Before
    fun setUp() {
        every { mockGetModelsUseCase() } returns flowOf(models)
        every { mockIsFirstTimeLaunchPreferencesUseCase() } returns flowOf(false)
        coEvery { mockUpdateFirstTimeLaunchPreferencesUseCase(any()) } just Runs

        initViewModel()
    }

    @Test
    fun `When loading models successfully, it shows the model list`() = runTest {
        viewModel.uiModels.test {
            expectMostRecentItem() shouldBe models.map { it.toUiModel() }
        }
    }

    @Test
    fun `When loading models failed, it shows the corresponding error`() = runTest {
        val error = Exception()
        every { mockGetModelsUseCase() } returns flow { throw error }
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        viewModel.error.test {
            advanceUntilIdle()

            expectMostRecentItem() shouldBe error
        }
    }

    @Test
    fun `When loading models, it shows and hides loading correctly`() = runTest {
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        viewModel.isLoading.test {
            awaitItem() shouldBe false
            awaitItem() shouldBe true
            awaitItem() shouldBe false
        }
    }

    @Test
    fun `When calling navigate to Second, it navigates to Second screen`() = runTest {
        viewModel.navigator.test {
            viewModel.navigateToSecond(models[0].toUiModel())

            expectMostRecentItem() shouldBe AppDestination.Second
        }
    }

    @Test
    fun `When launching the app for the first time, it executes the use case and emits value accordingly`() =
        runTest {
            viewModel.onFirstTimeLaunch()

            coVerify(exactly = 1) {
                mockUpdateFirstTimeLaunchPreferencesUseCase(false)
            }
            viewModel.isFirstTimeLaunch.first() shouldBe false
        }

    private fun initViewModel(dispatchers: DispatchersProvider = coroutinesRule.testDispatcherProvider) {
        viewModel = HomeViewModel(
            mockGetModelsUseCase,
            mockIsFirstTimeLaunchPreferencesUseCase,
            mockUpdateFirstTimeLaunchPreferencesUseCase,
            dispatchers
        )
    }
}
