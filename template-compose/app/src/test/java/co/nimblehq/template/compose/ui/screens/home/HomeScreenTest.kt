package co.nimblehq.template.compose.ui.screens.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import co.nimblehq.template.compose.R
import co.nimblehq.template.compose.domain.model.Model
import co.nimblehq.template.compose.domain.usecase.UseCase
import co.nimblehq.template.compose.ui.AppDestination
import co.nimblehq.template.compose.ui.screens.BaseScreenTest
import co.nimblehq.template.compose.ui.screens.MainActivity
import co.nimblehq.template.compose.ui.theme.ComposeTheme
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
class HomeScreenTest : BaseScreenTest() {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val mockUseCase: UseCase = mockk()

    private lateinit var viewModel: HomeViewModel
    private var expectedAppDestination: AppDestination? = null

    @Before
    fun setUp() {
        every { mockUseCase() } returns flowOf(
            listOf(Model(1), Model(2), Model(3))
        )
    }

    @Test
    fun `When entering the Home screen, it shows UI correctly`() = initComposable {
        onNodeWithText(activity.getString(R.string.app_name)).assertIsDisplayed()
    }

    @Test
    fun `When entering the Home screen and loading the data failure, it shows the corresponding error`() {
        setStandardTestDispatcher()

        val error = Exception()
        every { mockUseCase() } returns flow { throw error }

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

        composeRule.activity.setContent {
            ComposeTheme {
                HomeScreen(
                    viewModel = viewModel,
                    navigator = { destination -> expectedAppDestination = destination }
                )
            }
        }
        testBody(composeRule)
    }

    private fun initViewModel() {
        viewModel = HomeViewModel(
            coroutinesRule.testDispatcherProvider,
            mockUseCase,
        )
    }
}
