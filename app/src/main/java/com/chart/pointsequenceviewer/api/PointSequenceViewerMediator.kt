package com.chart.pointsequenceviewer.api

import androidx.navigation.NavController
import com.chart.points.api.domain.model.PointSequenceId

interface PointSequenceViewerMediator {
    fun startFeature(navController: NavController, id: PointSequenceId)
}