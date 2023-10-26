package com.chart.pointsequenceviewer.impl

import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.chart.navigation.navigateWithDefaultAnim
import com.chart.points.api.domain.model.PointSequenceId
import com.chart.pointsequenceviewer.api.PointSequenceViewerMediator

class PointSequenceViewerMediatorImpl : PointSequenceViewerMediator {
    override fun startFeature(navController: NavController, id: PointSequenceId) {
        navController.navigateWithDefaultAnim(
            NavDeepLinkRequest.Builder
                .fromUri("chart://chart.app/view?id=${id.id}".toUri())
                .build(),
        )
    }
}