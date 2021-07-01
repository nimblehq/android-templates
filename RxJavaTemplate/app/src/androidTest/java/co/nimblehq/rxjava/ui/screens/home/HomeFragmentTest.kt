package co.nimblehq.rxjava.ui.screens.home

import androidx.test.filters.LargeTest
import co.nimblehq.rxjava.domain.repository.ApiRepository
import co.nimblehq.rxjava.ui.common.launchFragment
import co.nimblehq.rxjava.ui.common.waitForApiThen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@LargeTest
@HiltAndroidTest
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var testApiRepository: ApiRepository

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragment()
    }

    @Test
    fun showUiCorrectly() {
        with(Home) {
            verifyScreenWhileLoading()
            waitForApiThen { verifyScreenAfterLoading() }
        }
    }

    @Test
    fun clickOnRefreshButton_refreshData() {
        with(Home) {
            waitForApiThen {}
            clickOnRefreshButton()

            verifyScreenWhileLoading()
            waitForApiThen { verifyScreenAfterLoading() }
        }
    }

    private fun launchFragment() = launchFragment<HomeFragment>()
}
