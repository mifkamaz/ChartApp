package com.chart.pointsequenceviewer.impl.presentation.viewmodelfactory

import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.chart.di.findDependency
import com.chart.points.api.domain.model.PointSequenceId
import com.chart.pointsequenceviewer.impl.presentation.viewmodel.PointSequenceViewerViewModelImpl

class PointSequenceViewerViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        val application =
            checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
        val arguments = checkNotNull(extras[DEFAULT_ARGS_KEY])
        return PointSequenceViewerViewModelImpl(
            id = PointSequenceId(arguments.getString("id").orEmpty()),
            repository = application.findDependency()
        ) as T
    }
}