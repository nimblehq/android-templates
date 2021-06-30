package co.nimblehq.rxjava.ui.screens.second

import android.Manifest
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.GrantPermissionRule
import co.nimblehq.rxjava.R
import co.nimblehq.rxjava.domain.test.MockUtil
import co.nimblehq.rxjava.ui.screens.launchFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SecondFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var cameraPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @Before
    fun setup() {
        hiltRule.inject()
        Intents.init()
        launchFragment()
    }

    @Test
    fun when_initializing_it_should_show_the_UI_correctly() {
        onView(withId(R.id.ivSecondThumbnail)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSecondTitle)).check(matches(withText("title1")))
        onView(withId(R.id.tvSecondAuthor)).check(matches(withText("author1")))
        onView(withId(R.id.tvClickableText)).check(matches(withText("Clickable text")))
        onView(withId(R.id.btOpenPost))
            .check(matches(withText("Open Post")))
            .check(matches(isEnabled()))
        onView(withId(R.id.btOpenCamera))
            .check(matches(withText("Open Camera")))
            .check(matches(isEnabled()))
    }

    @Test
    fun when_clicking_on_the_open_camera_button_it_should_launch_the_devices_camera_app() {
        val activityResult = Instrumentation.ActivityResult(Activity.RESULT_OK, Intent())
        val expectedIntent = hasAction(MediaStore.ACTION_IMAGE_CAPTURE)
        intending(expectedIntent).respondWith(activityResult)

        onView(withId(R.id.btOpenCamera)).perform(click())

        intended(expectedIntent)
    }

    @After
    fun teardown() {
        Intents.release()
    }

    private fun launchFragment(bundle: SecondBundle = SecondBundle(MockUtil.dataList[0])) {
        val args = Bundle().apply {
            putParcelable("bundle", bundle)
        }

        launchFragment<SecondFragment>(args)
    }
}
