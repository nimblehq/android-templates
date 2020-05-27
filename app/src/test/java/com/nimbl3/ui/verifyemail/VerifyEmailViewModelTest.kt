package com.nimbl3.ui.verifyemail

import co.omise.gcpf.app.ui.screens.onboarding.otp.OtpBundle
import co.omise.gcpf.domain.data.error.ValidateError
import co.omise.gcpf.domain.usecase.ValidateEmailSingleUseCase
import co.omise.gcpf.domain.usecase.login.VerifyEmailSingleUseCase
import co.omise.gcpf.domain.usecase.register.VerifyEmailRegisterScbSingleUseCase
import co.omise.gcpf.domain.util.MockUtil
import com.nhaarman.mockitokotlin2.mock
import com.nimbl3.ui.base.NavigationEvent
import io.reactivex.Single
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test

class VerifyEmailViewModelTest {

    private val mockValidateEmailSingleUseCase = mock<ValidateEmailSingleUseCase>()
    private val mockVerifyEmailSingleUseCase = mock<VerifyEmailSingleUseCase>()
    private val mockVerifyEmailRegisterScbSingleUseCase =
        mock<VerifyEmailRegisterScbSingleUseCase>()
    private lateinit var viewModel: VerifyEmailViewModel

    @Before
    fun setup() {
        When calling mockValidateEmailSingleUseCase.execute(any()) itReturns Single.just("any")
        When calling mockVerifyEmailSingleUseCase.execute(any()) itReturns Single.just(MockUtil.preAuth)
        When calling mockVerifyEmailRegisterScbSingleUseCase.execute(any()) itReturns
            Single.just(MockUtil.registration)
        viewModel = VerifyEmailViewModelImpl(
            mockValidateEmailSingleUseCase,
            mockVerifyEmailSingleUseCase,
            mockVerifyEmailRegisterScbSingleUseCase
        )
    }

    @Test
    fun `Should not enable Next button when input is empty`() {
        val nextButtonObserver = viewModel.enableNextButton.test()

        viewModel.input.email("")

        nextButtonObserver
            .assertNoErrors()
            .assertValue(false)

        viewModel.input.email("example")

        nextButtonObserver
            .assertNoErrors()
            .assertValues(false, true)
    }

    @Test
    fun `When input is invalid, Should emit error event to react on next button clicked`() {
        val error = ValidateError.InvalidEmailError(cause = null)
        When calling mockValidateEmailSingleUseCase.execute(any()) itReturns Single.error(error)

        val errorObserver = viewModel.error.test()

        viewModel.input.bundle(VerifyEmailBundle.ForLogin)
        viewModel.input.email("example")
        viewModel.verifyEmail()

        errorObserver
            .assertNoErrors()
            .assertValue(error)
    }

    @Test
    fun `When verifying email in login flow, it should emit to navigate to EnterOtp with correct bundle`() {
        val navigatorObserver = viewModel.navigator.test()

        viewModel.input.bundle(VerifyEmailBundle.ForLogin)
        viewModel.input.email("email")
        viewModel.verifyEmail()

        navigatorObserver
            .assertNoErrors()
            .assertValue(
                NavigationEvent.OnBoarding.EnterOtp(
                    OtpBundle(
                        "email",
                        "token"
                    )
                )
            )
    }

    @Test
    fun `When verifying email in register scb flow, it should emit to navigate to EnterOtp with correct bundle`() {
        val navigatorObserver = viewModel.navigator.test()

        viewModel.input.bundle(VerifyEmailBundle.ForRegister("email", "pre-token"))
        viewModel.input.email("email")
        viewModel.verifyEmail()

        navigatorObserver
            .assertNoErrors()
            .assertValue(
                NavigationEvent.OnBoarding.EnterOtp(
                    OtpBundle(
                        "email",
                        "preAuthToken"
                    )
                )
            )
    }
}
