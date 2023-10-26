package com.chart.pointsequenceviewer.impl.presentation.viewmodel

import com.chart.mvi.UiAction
import com.chart.points.api.domain.model.PointSequenceId
import com.chart.points.api.domain.repository.PointRepository
import com.chart.pointsequenceviewer.impl.presentation.model.mvi.PointSequenceViewer

class PointSequenceViewerViewModelImpl(
    private val id: PointSequenceId,
    private val repository: PointRepository,
) : PointSequenceViewerViewModel() {

    override fun getInitialState() = PointSequenceViewer.State(
        points = repository.getPointSequence(id).points
    )

    override fun onAction(action: UiAction) { }

}