package com.chart.points.impl.data.datasource

import com.chart.points.api.domain.model.Point
import com.chart.points.api.domain.model.PointSequence
import com.chart.points.api.domain.model.PointSequenceId

interface PointsLocalDataSource {
    fun save(points: List<Point>): PointSequence
    fun get(id: PointSequenceId): PointSequence
}