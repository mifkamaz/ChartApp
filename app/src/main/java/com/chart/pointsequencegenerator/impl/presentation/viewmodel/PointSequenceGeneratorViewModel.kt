package com.chart.pointsequencegenerator.impl.presentation.viewmodel

import com.chart.mvi.MviViewModel
import com.chart.pointsequencegenerator.impl.presentation.model.mvi.PointSequenceGenerator

abstract class PointSequenceGeneratorViewModel :
    MviViewModel<PointSequenceGenerator.State, PointSequenceGenerator.Action, PointSequenceGenerator.Effect>()
