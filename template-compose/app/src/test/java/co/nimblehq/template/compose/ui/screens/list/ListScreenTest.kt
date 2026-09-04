package co.nimblehq.template.compose.ui.screens.list

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import co.nimblehq.template.compose.R
import co.nimblehq.template.compose.domain.usecases.UseCase
import co.nimblehq.template.compose.test.CoroutineTestRule
import co.nimblehq.template.compose.test.MockUtil
import co.nimblehq.template.compose.ui.theme.ComposeTheme
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
class ListScreenTest {

    @get:Rule
    val coroutinesRule = CoroutineTestRule()

    @get:Rule
    val composeRule = createComposeRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val mockUseCase: UseCase = mockk()

    private lateinit var viewModel: ListViewModel

    @Test
    fun `When entering the List screen, it shows the model list`() {
        every { mockUseCase() } returns flowOf(MockUtil.models)

        initComposable()

        MockUtil.models.forEach { model ->
            composeRule.onNodeWithText(model.id.toString()).assertIsDisplayed()
        }
    }

    @Test
    fun `When loading the data failed, it shows the corresponding error`() {
        val error = Exception()
        every { mockUseCase() } returns flow { throw error }

        initComposable()

        ShadowToast.showedToast(context.getString(R.string.error_generic)) shouldBe true
    }

    @Test
    fun `When tapping the go back button, it navigates back`() {
        every { mockUseCase() } returns flowOf(MockUtil.models)
        val onNavigateBack: () -> Unit = mockk(relaxed = true)

        initComposable(onNavigateBack = onNavigateBack)

        composeRule.onNodeWithText(context.getString(R.string.go_back)).performClick()
        composeRule.waitForIdle()

        verify { onNavigateBack() }
    }

    private fun initComposable(onNavigateBack: () -> Unit = {}) {
        viewModel = ListViewModel(coroutinesRule.testDispatcherProvider, mockUseCase)

        composeRule.setContent {
            ComposeTheme {
                ListScreen(viewModel = viewModel, onNavigateBack = onNavigateBack)
            }
        }
    }
}
