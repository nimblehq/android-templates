package com.nimbl3.ui.verifyemail

import android.os.Bundle
import co.omise.gcpf.app.R
import co.omise.gcpf.app.ui.screens.BaseFragmentTest
import co.omise.gcpf.app.ui.screens.onboarding.otp.OtpBundle
import co.omise.gcpf.app.util.extensions.*
import co.omise.gcpf.domain.data.error.LoginError
import co.omise.gcpf.domain.schedulers.TrampolineSchedulerProvider
import co.omise.gcpf.domain.usecase.ValidateEmailSingleUseCase
import co.omise.gcpf.domain.usecase.login.VerifyEmailSingleUseCase
import co.omise.gcpf.domain.usecase.register.VerifyEmailRegisterScbSingleUseCase
import co.omise.gcpf.domain.util.MockUtil
import com.nhaarman.mockitokotlin2.verify
import com.nimbl3.ui.OnBoardingNavigator
import com.nimbl3.ui.base.NavigationEvent
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_verify_email.*
import kotlinx.android.synthetic.main.toolbar_common.view.*
import kotlinx.android.synthetic.main.view_loading.*
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test

class VerifyEmailFragmentTest : BaseFragmentTest<VerifyEmailFragment, VerifyEmailViewModel>() {

    private val mockVerifyEmailSingleUseCase = mock<VerifyEmailSingleUseCase>()
    private val mockVerifyEmailRegisterScbSingleUseCase =
        mock<VerifyEmailRegisterScbSingleUseCase>()
    private val mockNavigator = mock<OnBoardingNavigator>()

    @Before
    fun setup() {
        When calling mockVerifyEmailSingleUseCase.execute(any()) itReturns
            Single.just(MockUtil.preAuth)
        When calling mockVerifyEmailRegisterScbSingleUseCase.execute(any()) itReturns
            Single.just(MockUtil.registration)
        launchFragment()
    }

    @Test
    fun `When initialize, Next button should be disabled`() {
        fragment.btVerifyEmailNext.shouldBeDisabled()
        fragment.btVerifyEmailNext.text shouldBeEqualTo fragment.getString(R.string.common_next)
    }

    @Test
    fun `When input correct email, click Next button should show loading`() {
        fragment.etVerifyEmail.setText("example@email.com")
        fragment.btVerifyEmailNext.shouldBeEnabled()

        When calling mockVerifyEmailSingleUseCase.execute(any()) itReturns
            Single.just(MockUtil.preAuth).delay()

        fragment.btVerifyEmailNext.performClick()
        fragment.pbLoading.shouldBeVisible()
    }

    @Test
    fun `When input incorrect email, click Next button should show error`() {
        fragment.etVerifyEmail.setText("example@")
        fragment.btVerifyEmailNext.performClick()

        fragment.tvVerifyEmailError.shouldBeVisible()
        fragment.tvVerifyEmailError.text shouldBeEqualTo fragment.getText(R.string.error_email_invalid)
    }

    @Test
    fun `When verify Email success in Login flow, should navigate to Otp screen`() {
        fragment.etVerifyEmail.setText("example@email.com")
        fragment.btVerifyEmailNext.performClick()

        fragment.pbLoading.shouldBeGone()
        verify(mockNavigator).navigate(
            NavigationEvent.OnBoarding.EnterOtp(OtpBundle("example@email.com", "token"))
        )
    }

    @Test
    fun `When verify Email failed, should show error message`() {
        val error = LoginError.VerifyEmailError(cause = null)
        When calling mockVerifyEmailSingleUseCase.execute(any()) itReturns Single.error(error)

        fragment.etVerifyEmail.setText("example@email.com")
        fragment.btVerifyEmailNext.performClick()

        fragment.pbLoading.shouldBeGone()
        fragment.tvVerifyEmailError.shouldBeVisible()
        // TODO test VerifyEmailError message
        fragment.tvVerifyEmailError.text shouldBeEqualTo fragment.getText(R.string.error_generic)
    }

    @Test
    fun `When initializing in Register SCB flow, it hides back button and pre-populates email`() {
        launchFragment(true)

        injectToolbar().run {
            ivToolbarBack.shouldNotBeNull().shouldBeInvisible()
        }
        fragment.etVerifyEmail.text.toString() shouldBeEqualTo "example@email.com"
    }

    @Test
    fun `When verify Email success in Register SCB flow, should navigate to Register SCB Otp screen`() {
        launchFragment(true)

        fragment.etVerifyEmail.setText("example@email.com")
        fragment.btVerifyEmailNext.performClick()

        fragment.pbLoading.shouldBeGone()
        verify(mockNavigator).navigate(
            NavigationEvent.OnBoarding.EnterOtp(OtpBundle("example@email.com", "preAuthToken"))
        )
    }

    private fun launchFragment(launchWithArgumentsForRegister: Boolean = false) {
        viewModel = VerifyEmailViewModelImpl(
            ValidateEmailSingleUseCase(TrampolineSchedulerProvider()),
            mockVerifyEmailSingleUseCase,
            mockVerifyEmailRegisterScbSingleUseCase
        )

        val args = if (launchWithArgumentsForRegister) {
            Bundle().apply {
                putParcelable(
                    "bundle", VerifyEmailBundle.ForRegister(
                        "example@email.com",
                        "token"
                    )
                )
            }
        } else {
            Bundle().apply {
                putParcelable(
                    "bundle", VerifyEmailBundle.ForLogin
                )
            }
        }

        launchFragment(VerifyEmailFragment().apply {
            navigator = mockNavigator
        }, args).onFragment { fragment = it }
    }

}
