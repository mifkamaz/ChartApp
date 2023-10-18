package com.chart.points.impl.data.mapper

import com.chart.points.api.domain.model.Point
import com.chart.points.impl.data.model.PointDTO

class PointsMapperImpl : PointsMapper {
    override fun mapGeneratePointsError(from: List<PointDTO>): List<Point> = from.map {
        Point(
            x = it.x,
            y = it.y,
        )
    }
}