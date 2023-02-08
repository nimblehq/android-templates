package co.nimblehq.template.compose.ui.screens.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import co.nimblehq.template.compose.R
import co.nimblehq.template.compose.ui.AppDestination
import co.nimblehq.template.compose.ui.screens.MainActivity
import org.junit.*

class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private var expectedAppDestination: AppDestination? = null

    @Before
    fun setUp() {
        composeRule.activity.setContent {
            HomeScreen(
                navigator = { destination -> expectedAppDestination = destination }
            )
        }
    }

    @Test
    fun When_entering_the_Home_screen__it_shows_UI_correctly() {
        composeRule.run {
            onNodeWithText(activity.getString(R.string.app_name)).assertIsDisplayed()
        }
    }
}
