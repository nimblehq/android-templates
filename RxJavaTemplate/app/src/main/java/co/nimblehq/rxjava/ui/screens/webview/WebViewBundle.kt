package co.nimblehq.rxjava.ui.screens.webview

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WebViewBundle(val url: String) : Parcelable
