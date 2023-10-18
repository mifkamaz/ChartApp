package com.chart.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface UiAction
interface UiState
interface UiSideEffect

abstract class MviViewModel<State : UiState, Action : UiAction, Effect : UiSideEffect> :
    ViewModel() {

    private val mutableUiState: MutableStateFlow<State> by lazy { MutableStateFlow(getInitialState()) }
    val uiState: StateFlow<State> by lazy { mutableUiState.asStateFlow() }

    private val mutableUiSideEffect: MutableSharedFlow<Effect> by lazy {
        MutableSharedFlow(
            extraBufferCapacity = 1,
        )
    }
    val uiSideEffect: SharedFlow<Effect> = mutableUiSideEffect.asSharedFlow()

    abstract fun getInitialState(): State

    abstract fun onAction(action: Action)

    fun updateState(modifier: State.() -> State) {
        mutableUiState.update { it.modifier() }
    }

    protected suspend fun sendEffect(sideEffect: () -> Effect) {
        mutableUiSideEffect.emit(sideEffect())
    }

    protected fun trySendEffect(sideEffect: () -> Effect) {
        mutableUiSideEffect.tryEmit(sideEffect())
    }
}
