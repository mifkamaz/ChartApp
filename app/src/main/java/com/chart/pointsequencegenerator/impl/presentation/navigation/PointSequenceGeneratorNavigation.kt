package com.chart.pointsequencegenerator.impl.presentation.navigation

import com.chart.points.api.domain.model.PointSequenceId

interface PointSequenceGeneratorNavigation {
    fun openPointSequenceViewer(id: PointSequenceId)
}