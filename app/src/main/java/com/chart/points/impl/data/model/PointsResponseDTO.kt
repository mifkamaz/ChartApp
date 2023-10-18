package com.chart.points.impl.data.model

import kotlinx.serialization.Serializable

//TODO: Проверить работу приложения после обфускации.
@Serializable
data class PointsResponseDTO(
    val points: List<PointDTO>
)

@Serializable
data class PointDTO(
    val x: Float,
    val y: Float
)
