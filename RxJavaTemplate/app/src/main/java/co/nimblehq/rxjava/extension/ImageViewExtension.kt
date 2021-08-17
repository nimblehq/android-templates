package co.nimblehq.rxjava.extension

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import co.nimblehq.rxjava.R
import co.nimblehq.rxjava.di.modules.GlideApp
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

/**
 * Provide extension functions relates to ImageView and loading image mechanism.
 */
fun ImageView.loadImage(
    url: String,
    onSuccess: (() -> Unit)? = null,
    onError: (() -> Unit)? = null
) {
    GlideApp.with(context)
        .load(url)
        .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.black_20a)))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .fitCenter()
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onSuccess?.invoke()
                return false
            }

            override fun onLoadFailed(
                e: GlideException?, model: Any?,
                target: Target<Drawable>?, isFirstResource: Boolean
            ): Boolean {
                onError?.invoke()
                return false
            }
        })
        .into(this)
}
