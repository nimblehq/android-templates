package co.nimblehq.sample.compose.ui.screens.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import co.nimblehq.sample.compose.ui.AppDestination
import co.nimblehq.sample.compose.ui.screens.MainActivity
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalTestApi
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    /**
     * More test samples with Runtime Permissions https://alexzh.com/ui-testing-of-android-runtime-permissions/
     */
    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.CAMERA
    )

    private var expectedAppDestination: AppDestination? = null

    @Before
    fun setup() {
        composeRule.activity.setContent {
            HomeScreen(
                navigator = { destination -> expectedAppDestination = destination }
            )
        }
    }

    @Test
    fun when_entering_the_Home_screen__it_shows_UI_correctly() {
        composeRule.run {
            onNodeWithText("Home").assertIsDisplayed()
        }
    }
}
