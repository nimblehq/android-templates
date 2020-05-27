package com.nimbl3.ui

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import co.omise.gcpf.app.R
import co.omise.gcpf.app.ui.screens.main.MainActivity
import co.omise.gcpf.app.ui.screens.onboarding.error.RegistrationErrorBundle
import co.omise.gcpf.app.ui.screens.onboarding.login.LoginOptionsFragmentDirections.Companion.actionLoginOptionsFragmentToVerifyEmailFragment
import co.omise.gcpf.app.ui.screens.onboarding.otp.OtpBundle
import co.omise.gcpf.app.ui.screens.onboarding.otp.OtpFragmentDirections.Companion.actionOtpFragmentToPinFragment
import co.omise.gcpf.app.ui.screens.onboarding.pin.PinBundle
import co.omise.gcpf.app.ui.screens.onboarding.termsandconditions.TermsAndConditionsFragmentDirections.Companion.actionTermsAndConditionsFragmentToVerifyIdOrPassportFragment
import co.omise.gcpf.app.ui.screens.onboarding.verifyemail.VerifyEmailBundle
import co.omise.gcpf.app.ui.screens.onboarding.verifyemail.VerifyEmailFragmentDirections.Companion.actionVerifyEmailFragmentToOtpFragment
import co.omise.gcpf.app.ui.screens.onboarding.verifyidorpassport.VerifyIdOrPassportFragmentDirections.Companion.actionVerifyIdOrPassportFragmentToVerifyEmailFragment
import co.omise.gcpf.app.ui.screens.splash.loading.SplashFragmentDirections.Companion.actionSplashFragmentToRegistrationErrorFragment
import co.omise.gcpf.app.ui.screens.splash.loading.SplashFragmentDirections.Companion.actionSplashFragmentToTermsAndConditionsFragment
import com.nhaarman.mockitokotlin2.verify
import com.nimbl3.ui.base.NavigationEvent
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers


@RunWith(RobolectricTestRunner::class)
class OnBoardingNavigatorTest {

    private lateinit var navigator: OnBoardingNavigator
    private val mockActivity = mock<OnboardingActivity>()
    private val mockNavController = mock<NavController>()
    private val mockDestination = mock<NavDestination>()

    @Before
    fun setup() {
        navigator = OnBoardingNavigatorImpl(mockActivity)
        ReflectionHelpers.setField(navigator, "navController", mockNavController)

        When calling mockNavController.currentDestination itReturns mockDestination
    }

    @Test
    fun `Should navigate to LoginOptions screen correctly`() {
        navigator.navigate(
            NavigationEvent.OnBoarding.LoginOptions
        )

        verify(mockNavController).popBackStack(R.id.loginOptionsFragment, false)
    }

    @Test
    fun `Should navigate to VerifyEmail screen correctly`() {
        When calling mockDestination.id itReturns R.id.loginOptionsFragment
        val bundle = VerifyEmailBundle.ForLogin
        navigator.navigate(
            NavigationEvent.OnBoarding.VerifyEmail(bundle)
        )

        verify(mockNavController).navigate(
            actionLoginOptionsFragmentToVerifyEmailFragment(bundle)
        )
    }

    @Test
    fun `Should navigate to EnterOtp screen correctly`() {
        When calling mockDestination.id itReturns R.id.verifyEmailFragment
        val bundle = OtpBundle("example@email.com", "token")
        navigator.navigate(
            NavigationEvent.OnBoarding.EnterOtp(bundle)
        )

        verify(mockNavController).navigate(
            actionVerifyEmailFragmentToOtpFragment(bundle)
        )
    }

    @Test
    fun `Should navigate to EnterPin screen correctly`() {
        When calling mockDestination.id itReturns R.id.otpFragment
        val bundle = PinBundle("example@email.com", "token")
        navigator.navigate(
            NavigationEvent.OnBoarding.EnterPin(bundle)
        )

        verify(mockNavController).navigate(
            actionOtpFragmentToPinFragment(bundle)
        )
    }

    @Test
    fun `Should navigate to Home screen correctly`() {
        navigator.navigate(
            NavigationEvent.OnBoarding.Home
        )

        val argument = ArgumentCaptor.forClass(Intent::class.java)
        verify(mockActivity).startActivity(argument.capture())
        verify(mockActivity).finish()
        argument.value.component!!.className shouldBeEqualTo MainActivity::class.java.name
    }

    @Test
    fun `Should open url intent correctly`() {
        navigator.navigate(
            NavigationEvent.OnBoarding.ScbAuthorize("url")
        )

        val argument = ArgumentCaptor.forClass(Intent::class.java)
        verify(mockActivity).startActivity(argument.capture())
        verify(mockActivity).finish()
        argument.value.action!! shouldBeEqualTo "android.intent.action.VIEW"
        argument.value.data!!.toString() shouldBeEqualTo "url"
    }

    @Test
    fun `Should navigate to TermsAndConditions screen correctly`() {
        When calling mockDestination.id itReturns R.id.splashFragment
        val preAuthToken = "preAuthToken"
        navigator.navigate(
            NavigationEvent.OnBoarding.TermsAndConditions(preAuthToken)
        )

        verify(mockNavController).navigate(
            actionSplashFragmentToTermsAndConditionsFragment(preAuthToken)
        )
    }

    @Test
    fun `Should navigate to Error screen correctly`() {
        When calling mockDestination.id itReturns R.id.splashFragment
        val bundle = RegistrationErrorBundle("anyError")
        navigator.navigate(
            NavigationEvent.OnBoarding.Error(bundle)
        )

        verify(mockNavController).navigate(
            actionSplashFragmentToRegistrationErrorFragment(bundle)
        )
    }

    @Test
    fun `Should navigate to VerifyId screen correctly`() {
        When calling mockDestination.id itReturns R.id.termsAndConditionsFragment
        val preAuthToken = "preAuthToken"
        navigator.navigate(
            NavigationEvent.OnBoarding.VerifyId(preAuthToken)
        )

        verify(mockNavController).navigate(
            actionTermsAndConditionsFragmentToVerifyIdOrPassportFragment()
        )
    }

    @Test
    fun `Should navigate to VerifyEmail from Register Verify ID screen correctly`() {
        When calling mockDestination.id itReturns R.id.verifyIdOrPassportFragment
        val bundle = VerifyEmailBundle.ForRegister(
            "example@email.com",
            "token"
        )
        navigator.navigate(
            NavigationEvent.OnBoarding.VerifyEmail(bundle)
        )

        verify(mockNavController).navigate(
            actionVerifyIdOrPassportFragmentToVerifyEmailFragment(bundle)
        )
    }
}
