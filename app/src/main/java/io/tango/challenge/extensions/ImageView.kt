package io.tango.challenge.extensions

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.Dimension
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

fun ImageView.loadUrlImage(
    item: String?,
    cornerType: CornerType = CornerType.None,
    isBlur: Boolean = false
) {
    if (item == null) return
    var requestOptions = when (cornerType) {
        is CornerType.All -> RequestOptions.bitmapTransform(
            RoundedCornersTransformation(
                dpToPx(context, cornerType.radius).toInt(),
                0,
                RoundedCornersTransformation.CornerType.ALL
            )
        )
        is CornerType.None -> RequestOptions()
        is CornerType.Circle -> RequestOptions.circleCropTransform()
        is CornerType.Top -> RequestOptions.bitmapTransform(
            MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(
                    dpToPx(context, cornerType.radius).toInt(),
                    0,
                    RoundedCornersTransformation.CornerType.TOP
                )
            )
        )
    }
    requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)

    if (isBlur) {
        Glide.with(context)
            .load(item)
            .transition(DrawableTransitionOptions.withCrossFade(300))
            .transform(BlurTransformation(25, 3))
            .apply(requestOptions)
            .into(this)
    } else {
        Glide.with(context)
            .load(item)
            .transition(DrawableTransitionOptions.withCrossFade(300))
            .apply(requestOptions)
            .into(this)
    }
}

sealed class CornerType {
    object None : CornerType()
    object Circle : CornerType()
    data class All(val radius: Int) : CornerType()
    data class Top(val radius: Int) : CornerType()
}

fun dpToPx(context: Context, @Dimension(unit = Dimension.DP) dp: Int): Float {
    val r = context.resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics)
}