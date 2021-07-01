package co.nimblehq.rxjava.extension

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import androidx.annotation.IdRes
import androidx.core.net.toUri

fun Context.showSoftKeyboard(view: View) {
    view.requestFocus()
    val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.showSoftInput(view, SHOW_IMPLICIT)
}

fun Context.getResourceName(@IdRes resId: Int?): String? =
    resId?.let { resources.getResourceName(resId) }

fun Context.openPlayStore(packageName: String) {
    val intent = Intent(Intent.ACTION_VIEW, "market://details?id=$packageName".toUri())
    if (intent.resolveActivity(packageManager) == null) {
        intent.data = "https://play.google.com/store/apps/details?id=$packageName".toUri()
    }
    startActivity(intent)
}

fun Context.checkAppInstalled(packageName: String): Boolean {
    return packageManager
        .getInstalledApplications(PackageManager.GET_META_DATA)
        .find { it.packageName == packageName } != null
}
