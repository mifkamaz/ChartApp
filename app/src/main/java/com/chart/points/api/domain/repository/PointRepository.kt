package com.chart.points.api.domain.repository

import com.chart.points.api.domain.model.PointSequence
import com.chart.points.api.domain.model.PointSequenceId

interface PointRepository {
    suspend fun generatePointSequence(count: Int): PointSequence
    fun getPointSequence(id: PointSequenceId): PointSequence
}