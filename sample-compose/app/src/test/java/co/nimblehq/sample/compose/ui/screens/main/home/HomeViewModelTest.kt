package co.nimblehq.sample.compose.ui.screens.main.home

import app.cash.turbine.test
import co.nimblehq.sample.compose.domain.usecases.GetModelsUseCase
import co.nimblehq.sample.compose.domain.usecases.IsFirstTimeLaunchPreferencesUseCase
import co.nimblehq.sample.compose.domain.usecases.UpdateFirstTimeLaunchPreferencesUseCase
import co.nimblehq.sample.compose.test.CoroutineTestRule
import co.nimblehq.sample.compose.test.MockUtil
import co.nimblehq.sample.compose.ui.models.toUiModel
import co.nimblehq.sample.compose.ui.screens.main.MainDestination
import co.nimblehq.sample.compose.util.DispatchersProvider
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
        viewModel.viewState.test {
            expectMostRecentItem().uiModels shouldBe MockUtil.models.map { it.toUiModel() }
        }
    }

    @Test
    fun `When loading models failed, it sends the corresponding error effect`() = runTest {
        val error = Exception()
        every { mockGetModelsUseCase() } returns flow { throw error }
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        viewModel.viewEffect.test {
            advanceUntilIdle()

            val effect = expectMostRecentItem()
            effect.shouldBeInstanceOf<HomeViewEffect.ShowError>()
            effect.error shouldBe error
        }
    }

    @Test
    fun `When loading models, it shows and hides loading correctly`() = runTest {
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        viewModel.viewState.test {
            awaitItem().isLoading shouldBe false
            awaitItem().isLoading shouldBe true
            awaitItem().uiModels shouldBe MockUtil.models.map { it.toUiModel() }.toImmutableList()
            awaitItem().isLoading shouldBe false
        }
    }

    @Test
    fun `When receiving the ItemClick intent, it navigates to Second screen`() = runTest {
        viewModel.viewEffect.test {
            viewModel.onIntent(HomeViewIntent.ItemClick(MockUtil.models[0].toUiModel()))

            expectMostRecentItem() shouldBe HomeViewEffect.Navigate(MainDestination.Second)
        }
    }

    @Test
    fun `When receiving the ItemLongClick intent, it navigates to Third screen`() = runTest {
        viewModel.viewEffect.test {
            viewModel.onIntent(HomeViewIntent.ItemLongClick(MockUtil.models[0].toUiModel()))

            expectMostRecentItem() shouldBe HomeViewEffect.Navigate(MainDestination.Third)
        }
    }

    @Test
    fun `When launching the app for the first time, it shows the message and updates the preference`() =
        runTest {
            every { mockIsFirstTimeLaunchPreferencesUseCase() } returns flowOf(true)
            initViewModel()

            viewModel.viewEffect.test {
                awaitItem() shouldBe HomeViewEffect.ShowFirstTimeLaunchMessage
            }
            coVerify(exactly = 1) {
                mockUpdateFirstTimeLaunchPreferencesUseCase(false)
            }
        }

    @Test
    fun `When launching the app NOT for the first time, it does not show the message or update the preference`() =
        runTest {
            coVerify(exactly = 0) {
                mockUpdateFirstTimeLaunchPreferencesUseCase(any())
            }
        }

    @Test
    fun `When initializing the ViewModel and isFirstTimeLaunchPreferencesUseCase returns error, it sends the corresponding error effect`() =
        runTest {
            val error = Exception()
            every { mockIsFirstTimeLaunchPreferencesUseCase() } returns flow { throw error }

            initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

            viewModel.viewEffect.test {
                advanceUntilIdle()

                val effect = expectMostRecentItem()
                effect.shouldBeInstanceOf<HomeViewEffect.ShowError>()
                effect.error shouldBe error
            }
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
