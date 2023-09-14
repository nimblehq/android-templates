package co.nimblehq.rxjava.ui.screens.second

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import co.nimblehq.rxjava.R

object Second {

    fun verifyScreen(title: String, author: String) {
        onView(withId(R.id.ivSecondThumbnail)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSecondTitle)).check(matches(withText(title)))
        onView(withId(R.id.tvSecondAuthor)).check(matches(withText(author)))
        onView(withId(R.id.tvClickableText)).check(matches(withText("Clickable text")))
        onView(withId(R.id.btOpenPost))
            .check(matches(withText("Open Post")))
            .check(matches(isEnabled()))
        onView(withId(R.id.btOpenCamera))
            .check(matches(withText("Open Camera")))
            .check(matches(isEnabled()))
    }

    fun clickOnOpenCameraButton() {
        onView(withId(R.id.btOpenCamera)).perform(click())
    }

    fun clickOnOpenPostButton() {
        onView(withId(R.id.btOpenPost)).perform(click())
    }
}
