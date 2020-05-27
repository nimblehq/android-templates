package com.nimbl3.ui.verifyemail

import co.omise.gcpf.app.ui.base.BaseViewModel
import co.omise.gcpf.app.ui.screens.onboarding.otp.OtpBundle
import co.omise.gcpf.domain.usecase.ValidateEmailSingleUseCase
import co.omise.gcpf.domain.usecase.login.VerifyEmailSingleUseCase
import co.omise.gcpf.domain.usecase.register.VerifyEmailRegisterScbSingleUseCase
import com.nimbl3.ui.base.BaseViewModel
import com.nimbl3.ui.base.NavigationEvent
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

abstract class VerifyEmailViewModel : BaseViewModel() {

    abstract val input: Input

    abstract val enableNextButton: Observable<Boolean>

    abstract val navigator: Observable<NavigationEvent.OnBoarding>

    abstract fun verifyEmail()

    interface Input {

        fun bundle(input: VerifyEmailBundle)

        fun email(input: String)
    }
}

class VerifyEmailViewModelImpl @Inject constructor(
    private val validateEmailSingleUseCase: ValidateEmailSingleUseCase,
    private val verifyLoginEmailSingleUseCase: VerifyEmailSingleUseCase,
    private val verifyEmailRegisterScbSingleUseCase: VerifyEmailRegisterScbSingleUseCase
) : VerifyEmailViewModel(), VerifyEmailViewModel.Input {

    private val _bundle = BehaviorSubject.create<VerifyEmailBundle>()
    private val _email = BehaviorSubject.create<String>()
    private val _navigator = PublishSubject.create<NavigationEvent.OnBoarding>()

    override val input: Input
        get() = this

    override val enableNextButton: Observable<Boolean>
        get() = _email.map { it.isNotEmpty() }

    override val navigator: Observable<NavigationEvent.OnBoarding>
        get() = _navigator

    override fun bundle(input: VerifyEmailBundle) {
        _bundle.onNext(input)
    }

    override fun email(input: String) {
        _email.onNext(input)
    }

    override fun verifyEmail() {
        val email = _email.value.orEmpty()
        when (val bundle = _bundle.value) {
            is VerifyEmailBundle.ForLogin -> verifyLoginEmail(email)
            is VerifyEmailBundle.ForRegister -> verifyRegisterScbEmail(email, bundle.preAuthToken)
        }
    }

    private fun verifyLoginEmail(email: String) {
        validateEmailSingleUseCase
            .execute(email)
            .flatMap(verifyLoginEmailSingleUseCase::execute)
            .doOnError(_error::onNext)
            .doOnSubscribe { _showLoading.onNext(true) }
            .doFinally { _showLoading.onNext(false) }
            .map { it.preAuthToken }
            .subscribeBy(
                onSuccess = this::navigateToEnterOtp,
                onError = {}
            )
            .addToDisposables()
    }

    private fun verifyRegisterScbEmail(email: String, preAuthToken: String) {
        validateEmailSingleUseCase
            .execute(email)
            .map { VerifyEmailRegisterScbSingleUseCase.Input(it, preAuthToken) }
            .flatMap(verifyEmailRegisterScbSingleUseCase::execute)
            .doOnError(_error::onNext)
            .doOnSubscribe { _showLoading.onNext(true) }
            .doFinally { _showLoading.onNext(false) }
            .map { it.preAuthToken }
            .subscribeBy(
                onSuccess = this::navigateToEnterOtp,
                // TODO https://app.clubhouse.io/toyota-ewallet/story/480/integrate-as-a-user-if-my-email-already-exists-in-the-system-i-am-redirected-to-the-login-flow
                onError = {}
            )
            .addToDisposables()
    }

    private fun navigateToEnterOtp(preAuthToken: String) {
        val bundle = OtpBundle(
            email = _email.value.orEmpty(),
            preAuthToken = preAuthToken
        )
        _navigator.onNext(NavigationEvent.OnBoarding.EnterOtp(bundle))
    }
}
