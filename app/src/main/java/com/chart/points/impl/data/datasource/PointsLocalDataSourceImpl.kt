package com.chart.points.impl.data.datasource

import com.chart.points.api.domain.model.Point
import com.chart.points.api.domain.model.PointSequence
import com.chart.points.api.domain.model.PointSequenceId
import java.util.UUID

//TODO: Не переживает смерть процесса
class PointsLocalDataSourceImpl : PointsLocalDataSource {

    private val map: MutableMap<PointSequenceId, PointSequence> = mutableMapOf()

    override fun save(points: List<Point>): PointSequence {
        val id = PointSequenceId(UUID.randomUUID().toString())
        val sequence = PointSequence(
            id = id,
            points = points
        )
        map[id] = sequence
        return sequence
    }

    override fun get(id: PointSequenceId): PointSequence {
        return map[id]!!
    }
}