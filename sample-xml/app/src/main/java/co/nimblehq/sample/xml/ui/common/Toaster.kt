package co.nimblehq.sample.xml.ui.common

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Toaster @Inject constructor(@ApplicationContext private val context: Context) {

    private var toast: Toast? = null

    fun display(message: String) {
        toast?.cancel()
        toast = makeText(context, message, LENGTH_LONG).also { it.show() }
    }
}
