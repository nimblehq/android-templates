package template.nimbl3.extension

import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import template.nimbl3.di.GlideApp

fun ImageView.setImageUrl(url: String) {
    GlideApp.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .into(this)
}
