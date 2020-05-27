package com.nimbl3.ui.verifyemail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class VerifyEmailBundle : Parcelable {

    @Parcelize
    data class ForRegister(
        val email: String,
        val preAuthToken: String
    ) : VerifyEmailBundle()

    @Parcelize
    object ForLogin : VerifyEmailBundle()
}
