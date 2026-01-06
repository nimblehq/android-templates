package co.nimblehq.sample.compose.ui.screens.main.details

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.domain.usecases.GetDetailsUseCase
import co.nimblehq.sample.compose.domain.usecases.SearchUserUseCase
import co.nimblehq.sample.compose.navigation.Navigator
import co.nimblehq.sample.compose.test.MockUtil
import co.nimblehq.sample.compose.ui.screens.BaseScreenTest
import co.nimblehq.sample.compose.ui.screens.MainActivity
import co.nimblehq.sample.compose.ui.screens.details.DetailsScreen
import co.nimblehq.sample.compose.ui.screens.details.DetailsScreenUi
import co.nimblehq.sample.compose.ui.screens.details.DetailsViewModel
import co.nimblehq.sample.compose.ui.screens.login.LoginOrRegisterScreen
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
class DetailsScreenTest : BaseScreenTest() {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    lateinit var navigator: Navigator

    private val mockGetDetailsUseCase: GetDetailsUseCase = mockk()
    private val mockSearchUseCase: SearchUserUseCase = mockk()

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setUp() {
        every { mockGetDetailsUseCase(any()) } returns flowOf(MockUtil.models.first())
        every { mockSearchUseCase(any()) } returns flowOf(MockUtil.models)
    }

    @Test
    fun `When entering the Details screen and loading the details successfully, it shows the item correctly`() =
        initComposable(DetailsScreen.Details(id = 1)) {
            onNodeWithText(activity.getString(R.string.details_title)).isDisplayed()
            onNodeWithText("1").assertIsDisplayed()
        }

    @Test
    fun `When entering the Details screen and loading the details fails, it shows the corresponding error`() {
        setStandardTestDispatcher()

        val error = Exception()
        every { mockGetDetailsUseCase(any()) } returns flow { throw error }

        initComposable(DetailsScreen.Details(id = 1)) {
            composeRule.waitForIdle()
            advanceUntilIdle()

            ShadowToast.showedToast(activity.getString(R.string.error_generic)) shouldBe true
        }
    }

    @Test
    fun `When clicking Like button without being logged in, it navigates to Login screen`() {
        initComposable(DetailsScreen.Details(id = 1)) {
            // Click Like button
            composeRule.onNodeWithTag(activity.getString(R.string.test_tag_favorite_button)).performClick()

            navigator.backStack.last() shouldBe LoginOrRegisterScreen
        }
    }

    @Test
    fun `When clicking Like button when logged in, it change like state accordingly`() {
        initComposable(DetailsScreen.Details(id = 1)) {
            // Simulate user logged in
            viewModel.changeUsername("Test User")

            // Click Like button
            composeRule.onNodeWithTag(activity.getString(R.string.test_tag_favorite_button)).performClick()

            // Verify did not navigate to Login screen
            navigator.backStack.last() shouldNotBe LoginOrRegisterScreen
            // Verify Like button state changed
            viewModel.isLiked.value shouldBe true
        }
    }

    private fun initComposable(
        details: DetailsScreen,
        testBody: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.() -> Unit,
    ) {
        initViewModel(details)

        // inject test navigator into activity before setContent
        composeRule.activityRule.scenario.onActivity { activity ->
            navigator = activity.navigator
        }

        // Mock the current screen as DetailsScreen
        navigator.goTo(DetailsScreen.Details(1))

        composeRule.activity.setContent {
            ComposeTheme {
                DetailsScreenUi(
                    viewModel = viewModel,
                    onClickBack = navigator::goBack,
                    navigateToLoginOrRegister = {
                        navigator.goTo(LoginOrRegisterScreen)
                    }
                )
            }
        }
        testBody(composeRule)
    }

    private fun initViewModel(details: DetailsScreen) {
        viewModel = DetailsViewModel(
            details = details,
            getDetailsUseCase = mockGetDetailsUseCase,
            searchUserUseCase = mockSearchUseCase,
            dispatchersProvider = coroutinesRule.testDispatcherProvider
        )
    }
}
