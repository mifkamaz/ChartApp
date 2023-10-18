package com.chart.pointsequencegenerator.impl.presentation.model.mvi

import com.chart.mvi.UiAction
import com.chart.mvi.UiSideEffect
import com.chart.mvi.UiState

class PointSequenceGenerator {
    data class State(
        val loading: Boolean
    ) : UiState

    sealed class Effect : UiSideEffect {
        data class BusinessError(
            val message: String,
            val retryAction: Action
        ) : Effect()

        data object InputError : Effect()
        data class NetworkError(
            val retryAction: Action
        ) : Effect()
    }

    sealed class Action : UiAction {
        data class Submit(
            val count: Int
        ) : Action()
    }
}