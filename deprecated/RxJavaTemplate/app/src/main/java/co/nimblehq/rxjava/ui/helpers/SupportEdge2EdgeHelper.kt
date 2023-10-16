package co.nimblehq.rxjava.ui.helpers

import android.os.Build
import android.view.*
import androidx.annotation.Px
import androidx.core.view.*

/**
 * https://medium.com/androiddevelopers/gesture-navigation-handling-visual-overlaps-4aed565c134c
 */
@SuppressWarnings("LongMethod")
fun View.handleVisualOverlaps(
    marginInsteadOfPadding: Boolean = true,
    gravity: Int = Gravity.BOTTOM,
    forceInsetsApplying: Boolean = false
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P || forceInsetsApplying) {
        val marginTop = marginTop
        val paddingTop = paddingTop
        val marginBottom = marginBottom
        val paddingBottom = paddingBottom
        setOnApplyWindowInsetsListener { view, insets ->
            when (gravity) {
                Gravity.TOP -> {
                    val insetTop = insets.systemWindowInsetTop
                    if (marginInsteadOfPadding) {
                        view.updateMargin(top = insetTop + marginTop)
                    } else {
                        view.updatePaddingRelative(top = insetTop + paddingTop)
                    }
                }
                Gravity.BOTTOM -> {
                    val insetBottom = insets.systemWindowInsetBottom
                    if (marginInsteadOfPadding) {
                        view.updateMargin(bottom = insetBottom + marginBottom)
                    } else {
                        view.updatePaddingRelative(bottom = insetBottom + paddingBottom)
                    }
                }
            }
            insets
        }
    }
}

fun View.updateMargin(@Px top: Int = 0, @Px bottom: Int = 0) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(
        marginStart,
        if (top == 0) marginTop else top,
        marginEnd,
        if (bottom == 0) marginBottom else bottom
    )
    layoutParams = params
}
