package co.nimblehq.sample.compose.ui.screens.main.home

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import co.nimblehq.sample.compose.domain.usecases.GetModelsUseCase
import co.nimblehq.sample.compose.domain.usecases.IsFirstTimeLaunchPreferencesUseCase
import co.nimblehq.sample.compose.domain.usecases.UpdateFirstTimeLaunchPreferencesUseCase
import co.nimblehq.sample.compose.test.MockUtil
import co.nimblehq.sample.compose.test.TestDispatchersProvider
import co.nimblehq.sample.compose.ui.AppNavGraph
import co.nimblehq.sample.compose.ui.screens.MainActivity
import co.nimblehq.sample.compose.ui.screens.main.MainDestination
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()
    private lateinit var navController: TestNavHostController

    /**
     * More test samples with Runtime Permissions https://alexzh.com/ui-testing-of-android-runtime-permissions/
     */
    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.CAMERA
    )

    private val mockGetModelsUseCase: GetModelsUseCase = mockk(relaxed = true)
    private val mockIsFirstTimeLaunchPreferencesUseCase: IsFirstTimeLaunchPreferencesUseCase = mockk(relaxed = true)
    private val mockUpdateFirstTimeLaunchPreferencesUseCase: UpdateFirstTimeLaunchPreferencesUseCase =
        mockk(relaxed = true)

    // Cannot mock viewModel with mockk here because it will throw ClassCastException
    // Ref: https://github.com/mockk/mockk/issues/321
    @BindValue
    val viewModel: HomeViewModel = HomeViewModel(
        mockGetModelsUseCase,
        mockIsFirstTimeLaunchPreferencesUseCase,
        mockUpdateFirstTimeLaunchPreferencesUseCase,
        TestDispatchersProvider
    )

    @Before
    fun setUp() {
        hiltRule.inject()

        every { mockGetModelsUseCase() } returns flowOf(MockUtil.models)
        every { mockIsFirstTimeLaunchPreferencesUseCase() } returns flowOf(false)
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
    fun when_clicking_on_a_list_item__it_navigates_to_Second_screen() = initComposableNavigation {
        onNodeWithText("1").performClick()

        onNodeWithText("Second").assertIsDisplayed()

        navController.currentBackStackEntry?.destination?.hasRoute(MainDestination.Second.route, null)
    }

    @Test
    fun when_long_clicking_on_a_list_item_and_click_edit__it_navigates_to_Third_screen() = initComposableNavigation {
        onNodeWithText("1").performTouchInput { longClick() }

        onNodeWithText("Edit").performClick()

        onNodeWithText("Third").assertIsDisplayed()

        navController.currentBackStackEntry?.destination?.hasRoute(MainDestination.Third.route, null)
    }

    private fun initComposable(
        testBody: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.() -> Unit
    ) {
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            ComposeTheme {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToSecondScreen = {},
                    onNavigateToThirdScreen = {},
                )
            }
        }
        testBody(composeRule)
    }

    private fun initComposableNavigation(
        testBody: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.() -> Unit
    ) {
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            ComposeTheme {
                AppNavGraph(navController)
            }
        }
        testBody(composeRule)
    }
}
