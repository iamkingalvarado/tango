package io.tango.challenge.core.domain.managers

import android.view.View

interface SkeletonManager {
    fun showSkeleton(view: View, layoutId: Int)
    fun hideSkeleton()
}
