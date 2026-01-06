package co.nimblehq.sample.compose.ui.screens.main.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.domain.usecases.GetModelsUseCase
import co.nimblehq.sample.compose.domain.usecases.IsFirstTimeLaunchPreferencesUseCase
import co.nimblehq.sample.compose.domain.usecases.UpdateFirstTimeLaunchPreferencesUseCase
import co.nimblehq.sample.compose.navigation.Navigator
import co.nimblehq.sample.compose.test.MockUtil
import co.nimblehq.sample.compose.ui.screens.BaseScreenTest
import co.nimblehq.sample.compose.ui.screens.MainActivity
import co.nimblehq.sample.compose.ui.screens.details.DetailsScreen
import co.nimblehq.sample.compose.ui.screens.list.ListScreenUi
import co.nimblehq.sample.compose.ui.screens.list.ListViewModel
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
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
class ListScreenTest : BaseScreenTest() {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    lateinit var navigator: Navigator

    /**
     * More test samples with Runtime Permissions https://alexzh.com/ui-testing-of-android-runtime-permissions/
     */
    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.CAMERA
    )

    private val mockGetModelsUseCase: GetModelsUseCase = mockk()
    private val mockIsFirstTimeLaunchPreferencesUseCase: IsFirstTimeLaunchPreferencesUseCase =
        mockk()
    private val mockUpdateFirstTimeLaunchPreferencesUseCase: UpdateFirstTimeLaunchPreferencesUseCase =
        mockk()

    private lateinit var viewModel: ListViewModel

    @Before
    fun setUp() {
        every { mockGetModelsUseCase() } returns flowOf(MockUtil.models)
        every { mockIsFirstTimeLaunchPreferencesUseCase() } returns flowOf(false)
        coEvery { mockUpdateFirstTimeLaunchPreferencesUseCase(any()) } just Runs
    }

    @Test
    fun `When entering the List screen for the first time, it shows a toast confirming that`() {
        every { mockIsFirstTimeLaunchPreferencesUseCase() } returns flowOf(true)

        initComposable {
            composeRule.waitForIdle()
            advanceUntilIdle()

            ShadowToast.showedToast(activity.getString(R.string.message_first_time_launch)) shouldBe true
        }
    }

    @Test
    fun `When entering the List screen NOT for the first time, it doesn't show the toast confirming that`() {
        initComposable {
            composeRule.waitForIdle()
            advanceUntilIdle()

            ShadowToast.showedToast(activity.getString(R.string.message_first_time_launch)) shouldBe false
        }
    }

    @Test
    fun `When entering the List screen and loading the list item successfully, it shows the list item correctly`() =
        initComposable {
            onNodeWithText(activity.getString(R.string.list_title)).assertIsDisplayed()

            onNodeWithText("1").assertIsDisplayed()
            onNodeWithText("2").assertIsDisplayed()
            onNodeWithText("3").assertIsDisplayed()
        }

    @Test
    fun `When entering the List screen and loading the list item failure, it shows the corresponding error`() {
        setStandardTestDispatcher()

        val error = Exception()
        every { mockGetModelsUseCase() } returns flow { throw error }

        initComposable {
            composeRule.waitForIdle()
            advanceUntilIdle()

            ShadowToast.showedToast(activity.getString(R.string.error_generic)) shouldBe true
        }
    }

    @Test
    fun `When clicking on a list item, it navigates to details screen`() = initComposable {
        onNodeWithText("1").performClick()

        navigator.backStack.last() shouldBe DetailsScreen.Details(1)
    }

    private fun initComposable(
        testBody: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.() -> Unit,
    ) {
        initViewModel()

        // inject test navigator into activity before setContent
        composeRule.activityRule.scenario.onActivity { activity ->
            navigator = activity.navigator
        }

        composeRule.activity.setContent {
            ComposeTheme {
                ListScreenUi(
                    viewModel = viewModel,
                    onItemClick = { detailsScreen ->
                        navigator.goTo(detailsScreen)
                    },
                    onClickSearch = {
                        // TODO
                    }
                )
            }
        }
        testBody(composeRule)
    }

    private fun initViewModel() {
        viewModel = ListViewModel(
            mockGetModelsUseCase,
            mockIsFirstTimeLaunchPreferencesUseCase,
            mockUpdateFirstTimeLaunchPreferencesUseCase,
            coroutinesRule.testDispatcherProvider
        )
    }
}
