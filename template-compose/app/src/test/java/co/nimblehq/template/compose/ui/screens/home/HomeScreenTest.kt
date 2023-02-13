package co.nimblehq.template.compose.ui.screens.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import co.nimblehq.template.compose.R
import co.nimblehq.template.compose.ui.AppDestination
import co.nimblehq.template.compose.ui.screens.MainActivity
import co.nimblehq.template.compose.ui.theme.ComposeTheme
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private var expectedAppDestination: AppDestination? = null

    @Before
    fun setUp() {
        // TODO more setup logic here
    }

    @Test
    fun `When entering the Home screen, it shows UI correctly`() = initComposable {
        onNodeWithText(activity.getString(R.string.app_name)).assertIsDisplayed()
    }

    private fun initComposable(
        testBody: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.() -> Unit
    ) {
        composeRule.activity.setContent {
            ComposeTheme {
                HomeScreen(
                    navigator = { destination -> expectedAppDestination = destination }
                )
            }
        }
        testBody(composeRule)
    }
}
