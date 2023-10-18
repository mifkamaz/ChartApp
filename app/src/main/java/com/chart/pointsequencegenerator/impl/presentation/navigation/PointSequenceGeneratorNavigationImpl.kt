package com.chart.pointsequencegenerator.impl.presentation.navigation

import androidx.navigation.NavController
import com.chart.points.api.domain.model.PointSequenceId
import com.chart.pointsequenceviewer.api.PointSequenceViewerMediator

//TODO: NavController утекает. Навигация перестаёт работать при перевороте (странно что не падает). Здесь нужно делать обёртку с bind/unbind navController
class PointSequenceGeneratorNavigationImpl(
    private val navController: NavController,
    private val pointSequenceViewerMediator: PointSequenceViewerMediator
) : PointSequenceGeneratorNavigation {

    //TODO: Решил опробовать подход с передачей id вместо объектов, как это рекомендует документация гугла
    //     https://developer.android.com/topic/modularization/patterns#communication
    //     не уверен в том, что использовал бы его в боевом приложении
    //     You shouldn't pass objects as navigation arguments. Instead, use simple ids that features can use to access and load desired resources from the data layer. This way, you keep the coupling low and don't violate the single source of truth principle.
    override fun openPointSequenceViewer(id: PointSequenceId) {
        pointSequenceViewerMediator.startFeature(navController, id)
    }
}