package com.chart.pointsequencegenerator.impl.presentation.viewmodel

import com.chart.corecoroutines.launch
import com.chart.points.api.domain.model.BusinessError
import com.chart.points.api.domain.model.InputError
import com.chart.points.api.domain.repository.PointRepository
import com.chart.pointsequencegenerator.impl.presentation.model.mvi.PointSequenceGenerator
import com.chart.pointsequencegenerator.impl.presentation.navigation.PointSequenceGeneratorNavigation
import timber.log.Timber

class PointSequenceGeneratorViewModelImpl(
    private val repository: PointRepository,
    private val navigation: PointSequenceGeneratorNavigation,
) : PointSequenceGeneratorViewModel() {

    override fun getInitialState() = PointSequenceGenerator.State(
        input = null,
        loading = false,
        validInput = false
    )

    override fun onAction(action: PointSequenceGenerator.Action) {
        when (action) {
            is PointSequenceGenerator.Action.InputChanged -> processInputChanged(action)
            is PointSequenceGenerator.Action.Submit -> processSubmit(action)
        }
    }

    private fun processInputChanged(action: PointSequenceGenerator.Action.InputChanged) {
        val inputDigits = action.input.toIntOrNull()
        updateState {
            copy(
                input = inputDigits,
                validInput = inputDigits != null && inputDigits > 0,
            )
        }
    }

    private fun processSubmit(action: PointSequenceGenerator.Action.Submit) {
        if (!uiState.value.validInput) return
        launch(
            block = {
                updateState { copy(loading = true) }
                val sequence = repository.generatePointSequence(
                    requireNotNull(uiState.value.input)
                )
                navigation.openPointSequenceViewer(sequence.id)
            },
            finallyBlock = {
                updateState { copy(loading = false) }
            },
            onError = {
                val effect = when (it) {
                    is BusinessError ->
                        PointSequenceGenerator.Effect.BusinessError(
                            it.message.orEmpty(),
                            action
                        )

                    is InputError ->
                        PointSequenceGenerator.Effect.InputError

                    else ->
                        PointSequenceGenerator.Effect.NetworkError(
                            action
                        )
                }
                trySendEffect { effect }
                Timber.Forest.e(it)
            },
        )
    }

}