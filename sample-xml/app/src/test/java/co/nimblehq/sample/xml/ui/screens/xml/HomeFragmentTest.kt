package co.nimblehq.sample.xml.ui.screens.xml

import androidx.core.view.isVisible
import co.nimblehq.sample.xml.databinding.FragmentHomeBinding
import co.nimblehq.sample.xml.model.UiModel
import co.nimblehq.sample.xml.test.TestNavigatorModule.mockMainNavigator
import co.nimblehq.sample.xml.test.getPrivateProperty
import co.nimblehq.sample.xml.test.replace
import co.nimblehq.sample.xml.ui.BaseFragmentTest
import co.nimblehq.sample.xml.ui.base.NavigationEvent
import co.nimblehq.sample.xml.ui.screens.home.HomeFragment
import co.nimblehq.sample.xml.ui.screens.home.HomeViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.*
import org.robolectric.shadows.ShadowToast
import java.util.*

@HiltAndroidTest
class HomeFragmentTest : BaseFragmentTest<HomeFragment, FragmentHomeBinding>() {

    private val mockViewModel = mockk<HomeViewModel>(relaxed = true)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `When launching fragment, it displays the recycler view`() {
        launchFragment()
        fragment.binding.rvHome.isVisible.shouldBeTrue()
    }

    @Test
    fun `When launching fragment and view model emits loading, it displays the progress bar`() {
        every { mockViewModel.isLoading } returns MutableStateFlow(true)

        launchFragment()
        fragment.binding.pbHome.isVisible.shouldBeTrue()
    }

    @Test
    fun `When launching fragment and view model does not emit loading, it does not display the progress bar`() {
        every { mockViewModel.isLoading } returns MutableStateFlow(false)

        launchFragment()
        fragment.binding.pbHome.isVisible.shouldBeFalse()
    }

    @Test
    fun `When launching fragment and view model emits list of item, it displays the recycler view with items`() {
        val items = arrayListOf(
            UiModel(UUID.randomUUID().toString()),
            UiModel(UUID.randomUUID().toString()),
            UiModel(UUID.randomUUID().toString())
        )
        every { mockViewModel.uiModels } returns MutableStateFlow(items)

        launchFragment()
        fragment.binding.rvHome.adapter?.itemCount shouldBe items.size
    }

    @Test
    fun `When launching fragment and view model emits first time launch, it displays a toast message`() {
        every { mockViewModel.isFirstTimeLaunch } returns MutableStateFlow(true)

        launchFragment()
        ShadowToast.getTextOfLatestToast() shouldBe "This is the first time launch"
    }

    @Test
    fun `When view model emits navigation event to second fragment, it should navigate to second screen`() {
        val uiModel = UiModel(UUID.randomUUID().toString())
        every { mockViewModel.navigator } returns MutableStateFlow(NavigationEvent.Second(uiModel))
        every { mockMainNavigator.navigate(any()) } returns Unit

        launchFragment()
        verify { mockMainNavigator.navigate(NavigationEvent.Second(uiModel)) }
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<HomeFragment>(
            onInstantiate = {
                replace(getPrivateProperty("viewModel"), mockViewModel)
                navigator = mockMainNavigator
            }
        ) {
            fragment = this
            fragment.navigator.shouldNotBeNull()
        }
    }
}
