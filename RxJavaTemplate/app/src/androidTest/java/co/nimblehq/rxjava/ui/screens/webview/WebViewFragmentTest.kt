package co.nimblehq.rxjava.ui.screens.webview

import android.os.Bundle
import androidx.test.espresso.web.assertion.WebViewAssertions.webMatches
import androidx.test.espresso.web.model.Atoms.getCurrentUrl
import androidx.test.espresso.web.sugar.Web.onWebView
import co.nimblehq.rxjava.di.modules.RepositoryModule
import co.nimblehq.rxjava.domain.test.MockUtil
import co.nimblehq.rxjava.ui.screens.launchFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(RepositoryModule::class)
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
    fun when_initialize_should_shows_ui_correctly() {
        onWebView()
            .withNoTimeout()
            .check(webMatches(getCurrentUrl(), containsString("google.com")))
    }

    private fun launchFragment(bundle: WebViewBundle = WebViewBundle(MockUtil.dataList[0].url)) {
        val args = Bundle().apply {
            putParcelable("bundle", bundle)
        }

        launchFragment<WebViewFragment>(args)
    }
}
