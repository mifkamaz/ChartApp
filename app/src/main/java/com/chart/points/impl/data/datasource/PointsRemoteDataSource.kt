package com.chart.points.impl.data.datasource

import com.chart.points.impl.data.model.PointsResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface PointsRemoteDataSource {

    @GET("test/points")
    suspend fun generatePoints(@Query("count") count: Int): PointsResponseDTO

}