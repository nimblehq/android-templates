package co.nimblehq.ui.screens.second

import android.os.Parcelable
import co.nimblehq.ui.screens.home.Data
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SecondBundle(
    val data: Data
) : Parcelable
