package co.nimblehq.template.compose.ui.screens.list

import app.cash.turbine.test
import co.nimblehq.template.compose.common.BaseViewState
import co.nimblehq.template.compose.common.ErrorEvent
import co.nimblehq.template.compose.domain.usecases.UseCase
import co.nimblehq.template.compose.test.CoroutineTestRule
import co.nimblehq.template.compose.test.MockUtil
import co.nimblehq.template.compose.ui.screens.list.model.ListUiModel
import co.nimblehq.template.compose.util.DispatchersProvider
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
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
class ListViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutineTestRule()

    private val mockUseCase: UseCase = mockk()

    private lateinit var viewModel: ListViewModel

    @Before
    fun setUp() {
        every { mockUseCase() } returns flowOf(MockUtil.models)

        initViewModel()
    }

    @Test
    fun `When loading models successfully, it shows the model list`() = runTest {
        viewModel.state.test {
            expectMostRecentItem() shouldBe BaseViewState.Loaded(
                uiModel = ListUiModel(MockUtil.models.mapNotNull { it.id })
            )
        }
    }

    @Test
    fun `When loading models, it shows and hides loading correctly`() = runTest {
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        viewModel.state.test {
            awaitItem() shouldBe BaseViewState.Initial()
            awaitItem() shouldBe BaseViewState.Loading(uiModel = null)
            advanceUntilIdle()
            awaitItem() shouldBe BaseViewState.Loaded(
                uiModel = ListUiModel(MockUtil.models.mapNotNull { it.id })
            )
        }
    }

    @Test
    fun `When loading models failed, it shows the corresponding error state`() = runTest {
        val error = Exception()
        every { mockUseCase() } returns flow { throw error }
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        viewModel.state.test {
            advanceUntilIdle()

            expectMostRecentItem() shouldBe BaseViewState.Error(error = error, uiModel = null)
        }
    }

    @Test
    fun `When loading models failed, it emits an error event`() = runTest {
        val error = Exception()
        every { mockUseCase() } returns flow { throw error }
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        viewModel.events.test {
            advanceUntilIdle()

            expectMostRecentItem() shouldBe ErrorEvent(error)
        }
    }

    @Test
    fun `When sending the LoadModels intent, it reloads the model list`() = runTest {
        viewModel.setIntent(ListIntent.LoadModels)

        viewModel.state.test {
            expectMostRecentItem() shouldBe BaseViewState.Loaded(
                uiModel = ListUiModel(MockUtil.models.mapNotNull { it.id })
            )
        }
    }

    private fun initViewModel(dispatchers: DispatchersProvider = coroutinesRule.testDispatcherProvider) {
        viewModel = ListViewModel(dispatchers, mockUseCase)
    }
}
