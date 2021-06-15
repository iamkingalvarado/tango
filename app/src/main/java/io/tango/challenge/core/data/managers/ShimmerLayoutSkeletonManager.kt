package io.tango.challenge.core.data.managers

import android.view.View
import androidx.core.content.ContextCompat
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.SkeletonConfig
import com.faltenreich.skeletonlayout.createSkeleton
import com.faltenreich.skeletonlayout.mask.SkeletonShimmerDirection
import io.tango.challenge.R
import io.tango.challenge.core.domain.managers.SkeletonManager
import javax.inject.Inject

class ShimmerLayoutSkeletonManager @Inject constructor() : SkeletonManager {

    private var skeleton: Skeleton? = null

    override fun showSkeleton(view: View, layoutId: Int) {
        skeleton = view.createSkeleton(config = SkeletonConfig(
            maskColor = ContextCompat.getColor(view.context, R.color.secondarySystemBackground),
            shimmerAngle = 30,
            shimmerDurationInMillis = 3000,
            showShimmer = true,
            maskCornerRadius = 8F,
            shimmerColor = ContextCompat.getColor(view.context, R.color.secondarySystemBackground),
            shimmerDirection = SkeletonShimmerDirection.LEFT_TO_RIGHT
        ))
        skeleton?.showSkeleton()
    }

    override fun hideSkeleton() {
        skeleton?.showOriginal()
    }
}
