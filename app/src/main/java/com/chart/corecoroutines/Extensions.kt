package com.chart.corecoroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.launchSafe(
    context: CoroutineContext = EmptyCoroutineContext,
    onError: (Throwable) -> Unit = {},
    finallyBlock: () -> Unit = {},
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit,
): Job = this.launch(context, start) {
    try {
        block(this@launch)
    } catch (e: CancellationException) {
        throw e
    } catch (throwable: Throwable) {
        onError(throwable)
    } finally {
        finallyBlock()
    }
}

fun ViewModel.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    onError: (Throwable) -> Unit = {},
    finallyBlock: () -> Unit = {},
    block: suspend CoroutineScope.() -> Unit,
): Job = viewModelScope.launchSafe(
    context = context,
    onError = onError,
    finallyBlock = finallyBlock,
    block = block,
)