package co.nimblehq.rxjava.ui.screens.second

import android.os.Parcelable
import co.nimblehq.rxjava.domain.data.Data
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SecondBundle(
    val data: Data
) : Parcelable
