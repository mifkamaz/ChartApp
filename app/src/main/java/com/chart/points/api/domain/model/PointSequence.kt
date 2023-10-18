package com.chart.points.api.domain.model

data class PointSequence(
    val id: PointSequenceId,
    val points: List<Point>
)

@JvmInline
value class PointSequenceId(
    val id: String
)

data class Point(
    val x: Float,
    val y: Float
)
