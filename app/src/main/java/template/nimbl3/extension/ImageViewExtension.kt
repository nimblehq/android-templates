package template.nimbl3.extension

import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import template.nimbl3.di.modules.GlideApp

/**
 * Provide extension functions relates to ImageView and loading image mechanism.
 */

fun ImageView.setImageUrl(url: String) {
    GlideApp.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .into(this)
}