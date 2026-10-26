package co.nimblehq.template.compose.ui.screens.home

import app.cash.turbine.test
import co.nimblehq.template.compose.common.NavigationEvent
import co.nimblehq.template.compose.navigation.entry.ListDestination
import co.nimblehq.template.compose.test.CoroutineTestRule
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutineTestRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel()
    }

    @Test
    fun `When sending the NavigateToList intent, it navigates to the List screen`() = runTest {
        viewModel.events.test {
            viewModel.setIntent(HomeIntent.NavigateToList)

            expectMostRecentItem() shouldBe NavigationEvent(ListDestination)
        }
    }
}
