package co.nimblehq.ui.screens.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    val content: String,
    val imageUrl: String
) : Parcelable
