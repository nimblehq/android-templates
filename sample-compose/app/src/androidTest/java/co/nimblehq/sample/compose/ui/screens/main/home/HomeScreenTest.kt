package co.nimblehq.sample.compose.ui.screens.main.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import co.nimblehq.sample.compose.domain.usecases.GetModelsUseCase
import co.nimblehq.sample.compose.domain.usecases.IsFirstTimeLaunchPreferencesUseCase
import co.nimblehq.sample.compose.domain.usecases.UpdateFirstTimeLaunchPreferencesUseCase
import co.nimblehq.sample.compose.test.MockUtil
import co.nimblehq.sample.compose.test.TestDispatchersProvider
import co.nimblehq.sample.compose.ui.screens.FakeNavigator
import co.nimblehq.sample.compose.ui.screens.MainActivity
import co.nimblehq.sample.compose.ui.screens.main.MainDestination
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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

    private val mockGetModelsUseCase: GetModelsUseCase = mockk()
    private val mockIsFirstTimeLaunchPreferencesUseCase: IsFirstTimeLaunchPreferencesUseCase = mockk()
    private val mockUpdateFirstTimeLaunchPreferencesUseCase: UpdateFirstTimeLaunchPreferencesUseCase = mockk()

    private lateinit var viewModel: HomeViewModel
    private lateinit var fakeNavigator: FakeNavigator

    @Before
    fun setUp() {
        every { mockGetModelsUseCase() } returns flowOf(MockUtil.models)
        every { mockIsFirstTimeLaunchPreferencesUseCase() } returns flowOf(false)

        viewModel = HomeViewModel(
            mockGetModelsUseCase,
            mockIsFirstTimeLaunchPreferencesUseCase,
            mockUpdateFirstTimeLaunchPreferencesUseCase,
            TestDispatchersProvider
        )
    }

    @Test
    fun when_entering_the_Home_screen__it_shows_UI_correctly() = initComposable {
        onNodeWithText("Home").assertIsDisplayed()
    }

    @Test
    fun when_loading_list_item_successfully__it_shows_the_list_item_correctly() = initComposable {
        onNodeWithText("1").assertIsDisplayed()
        onNodeWithText("2").assertIsDisplayed()
        onNodeWithText("3").assertIsDisplayed()
    }

    @Test
    fun when_clicking_on_a_list_item__it_navigates_to_Second_screen() = initComposable {
        onNodeWithText("1").performClick()

        assertEquals("1", (fakeNavigator.currentScreen() as? MainDestination.Second)?.id)
    }

    private fun initComposable(
        testBody: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.() -> Unit
    ) {
        fakeNavigator = FakeNavigator()
        composeRule.activity.setContent {
            ComposeTheme {
                HomeScreen(
                    viewModel = viewModel,
                    navigator = fakeNavigator
                )
            }
        }
        testBody(composeRule)
    }
}
