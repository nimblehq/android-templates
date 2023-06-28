package co.nimblehq.sample.compose.ui.screens.home

import app.cash.turbine.test
import co.nimblehq.sample.compose.domain.usecase.GetModelsUseCase
import co.nimblehq.sample.compose.domain.usecase.IsFirstTimeLaunchPreferencesUseCase
import co.nimblehq.sample.compose.domain.usecase.UpdateFirstTimeLaunchPreferencesUseCase
import co.nimblehq.sample.compose.model.toUiModel
import co.nimblehq.sample.compose.test.CoroutineTestRule
import co.nimblehq.sample.compose.test.MockUtil
import co.nimblehq.sample.compose.ui.AppDestination
import co.nimblehq.sample.compose.util.DispatchersProvider
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutineTestRule()

    private val mockGetModelsUseCase: GetModelsUseCase = mockk()
    private val mockIsFirstTimeLaunchPreferencesUseCase: IsFirstTimeLaunchPreferencesUseCase = mockk()
    private val mockUpdateFirstTimeLaunchPreferencesUseCase: UpdateFirstTimeLaunchPreferencesUseCase = mockk()

    private lateinit var viewModel: HomeViewModel

    private val isFirstTimeLaunch = false

    @Before
    fun setUp() {
        every { mockGetModelsUseCase() } returns flowOf(MockUtil.models)
        every { mockIsFirstTimeLaunchPreferencesUseCase() } returns flowOf(isFirstTimeLaunch)
        coEvery { mockUpdateFirstTimeLaunchPreferencesUseCase(any()) } just Runs

        initViewModel()
    }

    @Test
    fun `When loading models successfully, it shows the model list`() = runTest {
        viewModel.uiModels.test {
            expectMostRecentItem() shouldBe MockUtil.models.map { it.toUiModel() }
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
            viewModel.navigateToSecond(MockUtil.models[0].toUiModel())

            expectMostRecentItem() shouldBe AppDestination.Second
        }
    }

    @Test
    fun `When initializing the ViewModel, it emits whether the app is launched for the first time accordingly`() =
        runTest {
            viewModel.isFirstTimeLaunch.first() shouldBe isFirstTimeLaunch
        }

    @Test
    fun `When initializing the ViewModel and isFirstTimeLaunchPreferencesUseCase returns error, it shows the corresponding error`() =
        runTest {
            val error = Exception()
            every { mockIsFirstTimeLaunchPreferencesUseCase() } returns flow { throw error }

            initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

            viewModel.error.test {
                advanceUntilIdle()

                expectMostRecentItem() shouldBe error
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
