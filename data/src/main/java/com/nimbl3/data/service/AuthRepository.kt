package com.nimbl3.data.service

import co.omise.gcpf.domain.data.login.*
import co.omise.gcpf.service.extensions.transform
import co.omise.gcpf.service.remote.AuthService
import co.omise.gcpf.service.request.LoginRequest
import io.reactivex.Single
import javax.inject.Inject

interface AuthRepository {

    fun verifyEmail(
        email: String
    ): Single<AuthStatus.PreAuth>
}

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {

    override fun verifyEmail(
        email: String
    ): Single<AuthStatus.PreAuth> {
        val request = LoginRequest.VerifyEmail(email)
        return authService
            .verifyEmail(request)
            .transform()
            .map { it.toPreAuth() }
    }
}
