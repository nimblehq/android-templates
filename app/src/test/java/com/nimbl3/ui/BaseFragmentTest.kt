package com.nimbl3.ui

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.omise.gcpf.app.R
import co.omise.gcpf.app.ui.base.BaseFragment
import co.omise.gcpf.app.ui.base.BaseViewModel
import co.omise.gcpf.app.ui.common.Toaster
import co.omise.gcpf.app.util.extensions.application
import com.nhaarman.mockitokotlin2.mock
import org.junit.runner.RunWith
import org.mockito.stubbing.Answer
import org.robolectric.util.ReflectionHelpers

@RunWith(AndroidJUnit4::class)
abstract class BaseFragmentTest<F : BaseFragment<VM>, VM : BaseViewModel> {

    protected lateinit var fragment: F
    protected lateinit var viewModel: VM

    protected inline fun <reified F : BaseFragment<VM>> launchFragment(
        newFragmentInstance: F,
        fragmentArgs: Bundle? = null
    ) = launchFragmentInContainer<F>(
        factory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): F {
                return newFragmentInstance.apply {
                    viewModelFactory = mock(
                        defaultAnswer = Answer {
                            viewModel as Any
                        }
                    )
                    toaster = Toaster(application)
                }
            }
        },
        fragmentArgs = fragmentArgs,
        themeResId = R.style.AppTheme
    )

    protected fun injectToolbar() =
        Toolbar(fragment.requireContext()).apply {
            // FIXME find a better way to inject toolbar
            ReflectionHelpers.setField(fragment, "toolbar", this)
            ReflectionHelpers.callInstanceMethod<F>(fragment, "setupActionBar")
            ReflectionHelpers.callInstanceMethod<F>(fragment, "setupView")
        }
}
