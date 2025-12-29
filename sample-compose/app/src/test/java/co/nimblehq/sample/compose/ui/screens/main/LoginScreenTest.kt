package co.nimblehq.sample.compose.ui.screens.main

import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.test.ext.junit.rules.ActivityScenarioRule
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.di.modules.main.MainActivityModule
import co.nimblehq.sample.compose.ui.screens.BaseScreenTest
import co.nimblehq.sample.compose.ui.screens.FakeNavigator
import co.nimblehq.sample.compose.ui.screens.MainActivity
import co.nimblehq.sample.compose.ui.screens.details.DetailsScreen
import co.nimblehq.sample.compose.ui.screens.login.LoginOrRegisterScreen
import co.nimblehq.sample.compose.ui.screens.login.LoginScreen
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import co.nimblehq.sample.compose.util.LocalResultEventBus
import co.nimblehq.sample.compose.util.ResultEventBus
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
class LoginScreenTest : BaseScreenTest() {

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    val navigator = FakeNavigator()

    @Before
    fun setUp() {
        // Mock the current screen as LoginScreen
        navigator.addToBackStack(
            listOf(
                DetailsScreen(1),
                LoginOrRegisterScreen,
                LoginScreen
            )
        )
    }

    @Test
    fun `When entering the Details screen and loading the details successfully, it shows the item correctly`() =
        runTest {
            initComposable {
                composeRule.onNodeWithTag(activity.getString(R.string.test_tag_user_name_field)).isDisplayed()
            }
        }

    @Test
    fun `When entering the user name, it shows correct name in the text box`() = runTest {
        initComposable {
            composeRule.onNodeWithTag(activity.getString(R.string.test_tag_user_name_field)).performTextInput("Test")

            composeRule.onNodeWithText("Test").assertIsDisplayed()
        }
    }

    @Test
    fun `When clicking login, it navigates back to details screen`() = runTest {
        initComposable {
            composeRule.onNodeWithTag(activity.getString(R.string.test_tag_user_name_field)).performTextInput("Test")

            composeRule.onNodeWithTag(activity.getString(R.string.test_tag_login_button)).performClick()

            // Verify it navigated back to DetailsScreen
            navigator.currentScreenClass() shouldBe DetailsScreen::class
            composeRule.onNodeWithText(activity.getString(R.string.details_title)).isDisplayed()

            ShadowToast.showedToast(activity.getString(R.string.welcome_back, "Test")) shouldBe true
        }
    }

    /**
     * Consider this can be used in other tests to initialize the composable under test
     */
    private suspend fun initComposable(
        eventBus: ResultEventBus = ResultEventBus(),
        testBody: suspend AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.(eventBus: ResultEventBus) -> Unit,
    ) {
        // inject test navigator into activity before setContent
        composeRule.activityRule.scenario.onActivity { activity ->
            activity.navigator = navigator
        }

        composeRule.activity.setContent {
            ComposeTheme {
                CompositionLocalProvider(LocalResultEventBus.provides(eventBus)) {
                    NavDisplay(
                        backStack = navigator.backStack,
                        onBack = { navigator.goBack() },
                        // In order to add the `ViewModelStoreNavEntryDecorator` (see comment below for why)
                        // we also need to add the default `NavEntryDecorator`s as well. These provide
                        // extra information to the entry's content to enable it to display correctly
                        // and save its state.
                        entryDecorators = listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator()
                        ),
                        entryProvider = entryProvider {
                            setOf(
                                MainActivityModule.provideEntryProviderInstaller(navigator)
                            ).forEach { builder -> this.builder() }
                        },
                    )
                }
            }
        }
        testBody(composeRule, eventBus)
    }
}
