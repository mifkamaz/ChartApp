package com.chart.pointsequencegenerator.impl.presentation.navigation

import com.chart.navigation.NavControllerExecutor
import com.chart.points.api.domain.model.PointSequenceId
import com.chart.pointsequenceviewer.api.PointSequenceViewerMediator

class PointSequenceGeneratorNavigationImpl(
    private val navControllerExecutor: NavControllerExecutor,
    private val pointSequenceViewerMediator: PointSequenceViewerMediator
) : PointSequenceGeneratorNavigation {

    //TODO: Решил опробовать подход с передачей id вместо объектов, как это рекомендует документация гугла
    //     https://developer.android.com/topic/modularization/patterns#communication
    //     не уверен в том, что использовал бы его в боевом приложении
    //     You shouldn't pass objects as navigation arguments. Instead, use simple ids that features can use to access and load desired resources from the data layer. This way, you keep the coupling low and don't violate the single source of truth principle.
    override fun openPointSequenceViewer(id: PointSequenceId) {
        navControllerExecutor.execute {
            pointSequenceViewerMediator.startFeature(this, id)
        }
    }
}