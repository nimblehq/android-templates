package co.nimblehq.rxjava.ui.screens.second

import android.Manifest.permission.CAMERA
import android.app.Activity.RESULT_OK
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import co.nimblehq.rxjava.domain.test.MockUtil
import co.nimblehq.rxjava.ui.common.launchFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
@HiltAndroidTest
class SecondFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var cameraPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(CAMERA)

    @Before
    fun setup() {
        hiltRule.inject()
        Intents.init()
        launchFragment()
    }

    @Test
    fun showUiCorrectly() {
        Second.verifyScreen("title1", "author1")
    }

    @Test
    fun clickOnOpenCameraButton_launchDevicesCameraApp() {
        val activityResult = Instrumentation.ActivityResult(RESULT_OK, Intent())
        val expectedIntent = hasAction(ACTION_IMAGE_CAPTURE)
        intending(expectedIntent).respondWith(activityResult)

        Second.clickOnOpenCameraButton()

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
