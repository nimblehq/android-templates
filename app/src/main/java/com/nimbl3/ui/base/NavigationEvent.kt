package com.nimbl3.ui.base

import co.omise.gcpf.app.ui.screens.onboarding.error.RegistrationErrorBundle
import co.omise.gcpf.app.ui.screens.onboarding.otp.OtpBundle
import co.omise.gcpf.app.ui.screens.onboarding.pin.PinBundle
import co.omise.gcpf.app.ui.screens.onboarding.verifyemail.VerifyEmailBundle

sealed class NavigationEvent {

    sealed class Splash : NavigationEvent() {
        object Home : Splash()
    }

    sealed class OnBoarding : NavigationEvent() {
        object LoginOptions : OnBoarding()

        data class VerifyEmail(val bundle: VerifyEmailBundle) : OnBoarding()
        data class EnterOtp(val bundle: OtpBundle) : OnBoarding()
        data class EnterPin(val bundle: PinBundle) : OnBoarding()

        data class ScbAuthorize(val url: String) : OnBoarding()
        data class TermsAndConditions(val preAuthToken: String) : OnBoarding()
        data class VerifyId(val preAuthToken: String) : OnBoarding()

        object Home : OnBoarding()
        data class Error(val bundle: RegistrationErrorBundle) : OnBoarding()
    }

    sealed class Main : NavigationEvent() {
        object Home : Main()
        object LoginOptions : Main()
        object Account : Main()
        data class PromotionDetail(val url: String) : Main()
        data class MarketingDetail(val url: String) : Main()
    }
}
