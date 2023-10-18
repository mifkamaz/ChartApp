package com.chart.points.impl.data.mapper

import com.chart.points.api.domain.model.Point
import com.chart.points.impl.data.model.PointDTO

interface PointsMapper {
    fun mapGeneratePointsError(from: List<PointDTO>): List<Point>
}
