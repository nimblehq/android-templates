package co.nimblehq.rxjava.ui.screens

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.core.util.Preconditions
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.viewbinding.ViewBinding
import co.nimblehq.rxjava.EmptyHiltActivity
import co.nimblehq.rxjava.R
import co.nimblehq.rxjava.test.TestAppModule.testSchedulerProvider
import co.nimblehq.rxjava.ui.base.BaseFragment
import dagger.hilt.android.testing.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseFragmentTest<F : BaseFragment<VB>, VB : ViewBinding> {

    protected lateinit var fragment: F

    /**
     * Issue: launchFragmentInContainer from the androidx.fragment:fragment-testing library
     * is NOT possible to use right now as it uses a hardcoded Activity under the hood
     * (i.e. [EmptyFragmentActivity]) which is not annotated with @AndroidEntryPoint.
     *
     * As a workaround, use this function that is equivalent. It requires you to add
     * [HiltTestActivity] in the debug folder and include it in the debug AndroidManifest.xml file
     * as can be found in this project.
     * Read more: https://dagger.dev/hilt/testing
     * Solution: launchFragmentInHiltContainer is copied from architecture-sample repo, but has
     * been modified to receive input [onInstantiate] so the devs can create the fragment
     * instance with mock/ fake variables at the initial step.
     * Reference: https://github.com/android/architecture-samples/blob/dev-hilt/app/src/androidTest/java/com/example/android/architecture/blueprints/todoapp/HiltExt.kt
     */
    protected inline fun <reified F : BaseFragment<VB>> launchFragmentInHiltContainer(
        crossinline onInstantiate: F.() -> Unit,
        fragmentArgs: Bundle? = null,
        @StyleRes themeResId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
        crossinline action: F.() -> Unit = {}
    ) {
        val startActivityIntent = Intent.makeMainActivity(
            ComponentName(
                ApplicationProvider.getApplicationContext(),
                EmptyHiltActivity::class.java
            )
        ).putExtra(
            "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
            themeResId
        )

        ActivityScenario.launch<EmptyHiltActivity>(startActivityIntent).onActivity { activity ->
            val fragment = (activity.supportFragmentManager.fragmentFactory.instantiate(
                Preconditions.checkNotNull(F::class.java.classLoader),
                F::class.java.name
            ) as F).apply {
                schedulerProvider = testSchedulerProvider
                this.onInstantiate()
            }
            fragment.arguments = fragmentArgs
            activity.supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, fragment, "")
                .commitNow()

            fragment.action()
        }
    }
}
