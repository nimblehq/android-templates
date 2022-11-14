package co.nimblehq.sample.xml.ui.screens.xml

import androidx.core.view.isVisible
import co.nimblehq.sample.xml.databinding.FragmentHomeBinding
import co.nimblehq.sample.xml.test.TestNavigatorModule.mockMainNavigator
import co.nimblehq.sample.xml.test.getPrivateProperty
import co.nimblehq.sample.xml.test.replace
import co.nimblehq.sample.xml.ui.BaseFragmentTest
import co.nimblehq.sample.xml.ui.screens.home.HomeFragment
import co.nimblehq.sample.xml.ui.screens.home.HomeViewModel
import dagger.hilt.android.testing.*
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.mockk
import org.junit.*

@HiltAndroidTest
class HomeFragmentTest : BaseFragmentTest<HomeFragment, FragmentHomeBinding>() {

    private val mockViewModel = mockk<HomeViewModel>(relaxed = true)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `When initializing fragment, it displays the recycler view`() {
        launchFragment()
        fragment.binding.rvHome.isVisible.shouldBeTrue()
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
