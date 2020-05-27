package com.nimbl3.ui

import android.content.Intent
import androidx.core.net.toUri
import co.omise.gcpf.app.R
import co.omise.gcpf.app.ui.base.*
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
import com.nimbl3.ui.base.NavigationEvent
import javax.inject.Inject


interface OnBoardingNavigator : BaseNavigator

class OnBoardingNavigatorImpl @Inject constructor(
    activity: OnboardingActivity
) : BaseNavigatorImpl(activity), OnBoardingNavigator {

    override val navHostFragment = R.id.navHostFragment

    override fun navigate(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.OnBoarding.LoginOptions -> popBackTo(R.id.loginOptionsFragment)

            is NavigationEvent.OnBoarding.VerifyEmail -> navigateToVerifyEmail(event.bundle)
            is NavigationEvent.OnBoarding.EnterOtp -> navigateToEnterOtp(event.bundle)
            is NavigationEvent.OnBoarding.EnterPin -> navigateToEnterPin(event.bundle)

            is NavigationEvent.OnBoarding.ScbAuthorize -> launchScbAuthorize(event.url)
            is NavigationEvent.OnBoarding.TermsAndConditions -> navigateToTermsAndConditions(event.preAuthToken)
            is NavigationEvent.OnBoarding.VerifyId -> navigateToVerifyId()

            is NavigationEvent.OnBoarding.Home -> {
                MainActivity.show(activity)
                activity.finish()
            }
            is NavigationEvent.OnBoarding.Error -> navigateToError(event.bundle)
        }
    }

    private fun navigateToVerifyEmail(bundle: VerifyEmailBundle) {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.loginOptionsFragment -> navController.navigate(
                actionLoginOptionsFragmentToVerifyEmailFragment(bundle)
            )
            R.id.verifyIdOrPassportFragment -> navController.navigate(
                actionVerifyIdOrPassportFragmentToVerifyEmailFragment(bundle)
            )
            else -> unsupportedNavigation()
        }
    }

    private fun navigateToEnterOtp(bundle: OtpBundle) {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.verifyEmailFragment -> navController.navigate(
                actionVerifyEmailFragmentToOtpFragment(bundle)
            )
            else -> unsupportedNavigation()
        }
    }

    private fun navigateToEnterPin(bundle: PinBundle) {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.otpFragment -> navController.navigate(
                actionOtpFragmentToPinFragment(bundle)
            )
            else -> unsupportedNavigation()
        }
    }

    private fun navigateToTermsAndConditions(preAuthToken: String) {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.splashFragment -> navController.navigate(
                actionSplashFragmentToTermsAndConditionsFragment(preAuthToken)
            )
            else -> unsupportedNavigation()
        }
    }

    private fun navigateToVerifyId() {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.termsAndConditionsFragment -> navController.navigate(
                actionTermsAndConditionsFragmentToVerifyIdOrPassportFragment()
            )
            else -> unsupportedNavigation()
        }
    }

    private fun launchScbAuthorize(url: String) {
        // TODO https://app.clubhouse.io/toyota-ewallet/story/409 handle SCB app not found
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        activity.startActivity(intent)
        activity.finish()
    }

    private fun navigateToError(bundle: RegistrationErrorBundle) {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.splashFragment -> navController.navigate(
                actionSplashFragmentToRegistrationErrorFragment(bundle)
            )
            else -> unsupportedNavigation()
        }
    }
}
