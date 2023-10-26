package com.chart.pointsequenceviewer.impl.presentation.model.mvi

import com.chart.mvi.UiState
import com.chart.points.api.domain.model.Point

class PointSequenceViewer {
    data class State(
        val points: List<Point>
    ) : UiState
}