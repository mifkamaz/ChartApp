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
        loading = false
    )

    override fun onAction(action: PointSequenceGenerator.Action) {
        when (action) {
            is PointSequenceGenerator.Action.Submit -> processSubmit(action)
        }
    }

    private fun processSubmit(action: PointSequenceGenerator.Action.Submit) {
        launch(
            block = {
                updateState { copy(loading = true) }
                val sequence = repository.generatePointSequence(action.count)
                navigation.openPointSequenceViewer(sequence.id)
                updateState { copy(loading = false) }
            },
            onError = {
                updateState { copy(loading = false) }
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
            }
        )
    }

}