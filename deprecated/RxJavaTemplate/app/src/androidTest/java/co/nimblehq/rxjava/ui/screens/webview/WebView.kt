package co.nimblehq.rxjava.ui.screens.webview

import androidx.test.espresso.web.assertion.WebViewAssertions.webMatches
import androidx.test.espresso.web.model.Atoms.getCurrentUrl
import androidx.test.espresso.web.sugar.Web.onWebView
import org.hamcrest.Matchers.equalTo

object WebView {

    fun verifyScreen() {
        onWebView()
            .withNoTimeout()
            .check(webMatches(getCurrentUrl(), equalTo("https://www.google.com/")))
    }
}
