package co.nimblehq.rxjava.ui.screens.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import co.nimblehq.rxjava.R
import co.nimblehq.rxjava.ui.screens.home.DataAdapter.ViewHolder
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not

object Home {

    fun verifyScreenWhileLoading() {
        onView(withId(R.id.rvHomeData)).check(matches(isDisplayed()))
        onView(withId(R.id.pbLoading)).check(matches(isDisplayed()))
        onView(withId(R.id.btHomeRefresh)).check(matches(not(isEnabled())))
    }

    fun verifyScreenAfterLoading() {
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

    fun scrollToAndClickOnItem(position: Int) {
        onView(withId(R.id.rvHomeData))
            .perform(actionOnItemAtPosition<ViewHolder>(position, scrollTo()))
            .perform(actionOnItemAtPosition<ViewHolder>(position, click()))
    }

    fun clickOnRefreshButton() {
        onView(withId(R.id.btHomeRefresh)).perform(click())
    }
}
