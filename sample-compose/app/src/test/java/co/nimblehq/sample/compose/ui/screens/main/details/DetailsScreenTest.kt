package co.nimblehq.sample.compose.ui.screens.main.details

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.domain.usecases.GetDetailsUseCase
import co.nimblehq.sample.compose.navigation.Navigator
import co.nimblehq.sample.compose.test.MockUtil
import co.nimblehq.sample.compose.ui.screens.BaseScreenTest
import co.nimblehq.sample.compose.ui.screens.MainActivity
import co.nimblehq.sample.compose.ui.screens.details.DetailsScreen
import co.nimblehq.sample.compose.ui.screens.details.DetailsScreenUi
import co.nimblehq.sample.compose.ui.screens.details.DetailsViewModel
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import io.kotest.matchers.shouldBe
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

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setUp() {
        every { mockGetDetailsUseCase(any()) } returns flowOf(MockUtil.models.first())
    }

    @Test
    fun `When entering the Details screen and loading the details successfully, it shows the item correctly`() =
        initComposable {
            onNodeWithText(activity.getString(R.string.details_title)).isDisplayed()
            onNodeWithText("1").assertIsDisplayed()
        }

    @Test
    fun `When entering the Details screen and loading the details fails, it shows the corresponding error`() {
        setStandardTestDispatcher()

        val error = Exception()
        every { mockGetDetailsUseCase(any()) } returns flow { throw error }

        initComposable {
            composeRule.waitForIdle()
            advanceUntilIdle()

            ShadowToast.showedToast(activity.getString(R.string.error_generic)) shouldBe true
        }
    }

    private fun initComposable(
        testBody: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.() -> Unit,
    ) {
        initViewModel()

        // inject test navigator into activity before setContent
        composeRule.activityRule.scenario.onActivity { activity ->
            navigator = activity.navigator
        }

        // Mock the current screen as DetailsScreen
        navigator.goTo(DetailsScreen(1))

        composeRule.activity.setContent {
            ComposeTheme {
                DetailsScreenUi(
                    viewModel = viewModel,
                    onClickBack = navigator::goBack
                )
            }
        }
        testBody(composeRule)
    }

    private fun initViewModel() {
        viewModel = DetailsViewModel(
            DetailsScreen(id = 1),
            mockGetDetailsUseCase,
            coroutinesRule.testDispatcherProvider
        )
    }
}

