package com.chart.pointsequenceviewer.impl.presentation.viewmodel

import com.chart.mvi.MviViewModel
import com.chart.mvi.UiAction
import com.chart.mvi.UiSideEffect
import com.chart.pointsequenceviewer.impl.presentation.model.mvi.PointSequenceViewer

abstract class PointSequenceViewerViewModel :
    MviViewModel<PointSequenceViewer.State, UiAction, UiSideEffect>()
