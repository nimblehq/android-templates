package co.nimblehq.template.compose.ui.screens.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import co.nimblehq.template.compose.R
import co.nimblehq.template.compose.navigation.entry.ListDestination
import co.nimblehq.template.compose.navigation.navigator.AppNavigator
import co.nimblehq.template.compose.test.CoroutineTestRule
import co.nimblehq.template.compose.ui.theme.ComposeTheme
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HomeScreenTest {

    @get:Rule
    val coroutinesRule = CoroutineTestRule()

    @get:Rule
    val composeRule = createComposeRule()

    private val context = ApplicationProvider.getApplicationContext<android.content.Context>()

    private val mockNavigator: AppNavigator = mockk(relaxed = true)

    private lateinit var viewModel: HomeViewModel

    @Test
    fun `When entering the Home screen, it shows UI correctly`() {
        initComposable()

        composeRule.onNodeWithText(context.getString(R.string.app_name)).assertIsDisplayed()
    }

    @Test
    fun `When tapping the navigate to list button, it navigates to the List screen`() {
        initComposable()

        composeRule.onNodeWithText(context.getString(R.string.home_navigate_to_list)).performClick()
        composeRule.waitForIdle()

        verify { mockNavigator.goTo(ListDestination) }
    }

    private fun initComposable() {
        viewModel = HomeViewModel()

        composeRule.setContent {
            ComposeTheme {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigate = {},

                    )
            }
        }
    }
}
