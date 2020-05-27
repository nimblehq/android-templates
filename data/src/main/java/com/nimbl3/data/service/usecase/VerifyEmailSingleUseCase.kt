package com.nimbl3.data.service.usecase

import co.omise.gcpf.domain.data.error.LoginError.VerifyEmailError
import co.omise.gcpf.domain.data.login.AuthStatus
import co.omise.gcpf.domain.repository.AuthRepository
import co.omise.gcpf.domain.schedulers.BaseSchedulerProvider
import com.nimbl3.data.service.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class VerifyEmailSingleUseCase @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val repository: AuthRepository
) : SingleUseCase<String, AuthStatus.PreAuth>(
    schedulerProvider.io(),
    schedulerProvider.main(),
    ::VerifyEmailError
) {

    override fun create(input: String): Single<AuthStatus.PreAuth> {
        return repository.verifyEmail(input)
    }
}
