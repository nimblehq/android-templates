package co.nimblehq.rxjava.ui.screens.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.web.assertion.WebViewAssertions.webMatches
import androidx.test.espresso.web.model.Atoms.getCurrentUrl
import androidx.test.espresso.web.sugar.Web.onWebView
import co.nimblehq.rxjava.R
import co.nimblehq.rxjava.domain.repository.ApiRepository
import co.nimblehq.rxjava.repository.TEST_API_DELAY
import co.nimblehq.rxjava.ui.screens.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var testApiRepository: ApiRepository

    @Before
    fun setup() {
        hiltRule.inject()
        launchActivity()
    }

    @Test
    fun when_initializing_it_should_show_the_UI_correctly() {
        onView(withId(R.id.rvHomeData)).check(matches(isDisplayed()))
        onView(withId(R.id.pbLoading)).check(matches(isDisplayed()))
        onView(withId(R.id.btHomeRefresh)).check(matches(not(isEnabled())))
        waitForApi {
            onView(withId(R.id.pbLoading)).check(matches(not(isDisplayed())))
            onView(withId(R.id.btHomeRefresh)).check(matches(isEnabled()))
            onView(allOf(withId(R.id.tvDataTitle), withText("title1")))
                .check(matches(isDisplayed()))
            onView(allOf(withId(R.id.tvDataAuthor), withText("author1")))
                .check(matches(isDisplayed()))
            onView(allOf(withId(R.id.tvDataTitle), withText("title2")))
                .check(matches(isDisplayed()))
            onView(allOf(withId(R.id.tvDataAuthor), withText("author2")))
                .check(matches(isDisplayed()))
            onView(allOf(withId(R.id.tvDataTitle), withText("title3")))
                .check(matches(isDisplayed()))
            onView(allOf(withId(R.id.tvDataAuthor), withText("author3")))
                .check(matches(isDisplayed()))
        }
    }

    @Test
    fun when_clicking_on_the_refresh_button_it_should_refresh_the_data() {
        waitForApi {}
        onView(withId(R.id.btHomeRefresh)).perform(click())

        onView(withId(R.id.pbLoading)).check(matches(isDisplayed()))
        onView(withId(R.id.btHomeRefresh)).check(matches(not(isEnabled())))
        waitForApi {
            onView(withId(R.id.pbLoading)).check(matches(not(isDisplayed())))
            onView(withId(R.id.btHomeRefresh)).check(matches(isEnabled()))
        }
    }

    @Test
    fun when_clicking_on_an_item_it_should_navigate_to_the_Second_screen() {
        waitForApi {
            onView(withId(R.id.rvHomeData))
                .perform(actionOnItemAtPosition<DataAdapter.ViewHolder>(0, click()))

            onView(withId(R.id.tvSecondTitle)).check(matches(withText("title1")))
            onView(withId(R.id.tvSecondAuthor)).check(matches(withText("author1")))

            pressBack()
            onView(withId(R.id.rvHomeData))
                .perform(actionOnItemAtPosition<DataAdapter.ViewHolder>(1, scrollTo()))
                .perform(actionOnItemAtPosition<DataAdapter.ViewHolder>(1, click()))

            onView(withId(R.id.tvSecondTitle)).check(matches(withText("title2")))
            onView(withId(R.id.tvSecondAuthor)).check(matches(withText("author2")))

            pressBack()
            onView(withId(R.id.rvHomeData))
                .perform(actionOnItemAtPosition<DataAdapter.ViewHolder>(2, scrollTo()))
                .perform(actionOnItemAtPosition<DataAdapter.ViewHolder>(2, click()))

            onView(withId(R.id.tvSecondTitle)).check(matches(withText("title3")))
            onView(withId(R.id.tvSecondAuthor)).check(matches(withText("author3")))

            pressBack()

            onView(withId(R.id.rvHomeData)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun when_clicking_on_the_open_post_button_it_should_navigate_to_the_WebView_screen() {
        waitForApi {
            onView(withId(R.id.rvHomeData))
                .perform(actionOnItemAtPosition<DataAdapter.ViewHolder>(0, click()))
            onView(withId(R.id.btOpenPost)).perform(click())

            onWebView()
                .withNoTimeout()
                .check(webMatches(getCurrentUrl(), containsString("google.com")))

            pressBack()

            onView(withId(R.id.ivSecondThumbnail)).check(matches(isDisplayed()))

            pressBack()

            onView(withId(R.id.rvHomeData)).check(matches(isDisplayed()))
        }
    }

    private fun waitForApi(action: () -> Unit) {
        Thread.sleep(TEST_API_DELAY)
        action.invoke()
    }

    private fun launchActivity(): ActivityScenario<MainActivity>? {
        return launch(MainActivity::class.java).apply {
            onActivity { activity ->
                (activity.findViewById(R.id.rvHomeData) as RecyclerView).itemAnimator = null
            }
        }
    }
}
