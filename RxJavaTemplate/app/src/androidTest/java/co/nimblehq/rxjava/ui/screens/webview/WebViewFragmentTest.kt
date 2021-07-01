package co.nimblehq.rxjava.ui.screens.webview

import android.os.Bundle
import androidx.test.filters.LargeTest
import co.nimblehq.rxjava.domain.test.MockUtil
import co.nimblehq.rxjava.ui.common.launchFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
@HiltAndroidTest
class WebViewFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragment()
    }

    @Test
    fun showUiCorrectly() {
        WebView.verifyScreen()
    }

    private fun launchFragment(bundle: WebViewBundle = WebViewBundle(MockUtil.dataList[0].url)) {
        val args = Bundle().apply {
            putParcelable("bundle", bundle)
        }

        launchFragment<WebViewFragment>(args)
    }
}
