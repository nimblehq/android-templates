package com.nimbl3.extension

import android.app.Activity
import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * Provide extension functions to Activity/Fragment to capable of showing Toast in a shorter call
 */

fun Activity.toastShort(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.toastShort(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

fun Activity.toastLong(message: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.toastLong(message: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, message, duration).show()
}