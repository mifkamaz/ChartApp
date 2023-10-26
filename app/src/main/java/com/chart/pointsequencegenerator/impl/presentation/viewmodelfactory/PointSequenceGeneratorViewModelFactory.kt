package com.chart.pointsequencegenerator.impl.presentation.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.chart.di.findDependency
import com.chart.pointsequencegenerator.impl.presentation.navigation.PointSequenceGeneratorNavigationImpl
import com.chart.pointsequencegenerator.impl.presentation.viewmodel.PointSequenceGeneratorViewModelImpl

class PointSequenceGeneratorViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        val application =
            checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
        return PointSequenceGeneratorViewModelImpl(
            repository = application.findDependency(),
            navigation = PointSequenceGeneratorNavigationImpl(
                navControllerExecutor = application.findDependency(),
                pointSequenceViewerMediator = application.findDependency()
            )
        ) as T
    }
}