package co.nimblehq.sample.xml.ui.common

import android.content.Context
import android.graphics.Canvas
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import co.nimblehq.sample.xml.R


class ItemDivider(private val context: Context) : RecyclerView.ItemDecoration() {

    private val divider = AppCompatResources.getDrawable(context, R.drawable.divider)

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        divider?.let {
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            val childCount = parent.childCount
            for (i in 0 until childCount - 1) {
                val child: View = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top: Int = child.bottom + params.bottomMargin
                val bottom: Int = top + it.intrinsicHeight
                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }
        }
    }
}
