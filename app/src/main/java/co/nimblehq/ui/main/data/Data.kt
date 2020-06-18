package co.nimblehq.ui.main.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(val content: String,
                val imageUrl: String): Parcelable
