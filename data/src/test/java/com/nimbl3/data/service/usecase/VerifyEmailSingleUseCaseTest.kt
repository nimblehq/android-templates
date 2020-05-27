package com.nimbl3.data.service.usecase

import co.omise.gcpf.domain.data.error.LoginError
import co.omise.gcpf.domain.repository.AuthRepository
import co.omise.gcpf.domain.schedulers.TrampolineSchedulerProvider
import co.omise.gcpf.domain.util.MockUtil
import io.reactivex.Single
import org.amshove.kluent.*
import org.junit.Test

class VerifyEmailSingleUseCaseTest {

    @Test
    fun `When execute usecase request successfully, it returns positive result`() {
        val data = MockUtil.preAuth
        val mockRepository = mock<AuthRepository>()
        When calling mockRepository.verifyEmail(any()) itReturns Single.just(data)
        val useCase = VerifyEmailSingleUseCase(
            TrampolineSchedulerProvider(),
            mockRepository
        )

        val testSubscriber = useCase.execute("").test()
        testSubscriber
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue(data)
    }

    @Test
    fun `When execute usecase request failed, it returns wrapped error`() {
        val mockRepository = mock<AuthRepository>()
        When calling mockRepository.verifyEmail(any()) itReturns Single.error(
            Throwable()
        )
        val useCase = VerifyEmailSingleUseCase(
            TrampolineSchedulerProvider(),
            mockRepository
        )

        val testSubscriber = useCase.execute("").test()
        testSubscriber
            .assertError { it is LoginError.VerifyEmailError }
            .assertValueCount(0)
    }
}
