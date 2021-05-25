package co.nimblehq.coroutine.ui.screens.second

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SecondBundle(
    val message: String
) : Parcelable
