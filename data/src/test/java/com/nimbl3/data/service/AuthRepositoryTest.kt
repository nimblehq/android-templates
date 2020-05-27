package com.nimbl3.data.service

import co.omise.gcpf.domain.util.MockUtil
import co.omise.gcpf.service.remote.AuthService
import co.omise.gcpf.service.request.LoginRequest
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.amshove.kluent.*
import org.junit.Test
import retrofit2.Response

class AuthRepositoryTest {

    @Test
    fun `Should make verify email call with proper value for email`() {
        val email = "email@example.com"
        val mockService = mock<AuthService>()

        When calling mockService.verifyEmail(any()) itReturns Single.just(
            Response.success(MockUtil.preAuthResponse)
        )
        val repository = AuthRepositoryImpl(mockService)

        val testSubscriber = repository.verifyEmail(email).test()

        testSubscriber.awaitTerminalEvent()

        testSubscriber
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue(MockUtil.preAuth)

        verify(mockService).verifyEmail(
            eq(LoginRequest.VerifyEmail(email))
        )
    }

}
