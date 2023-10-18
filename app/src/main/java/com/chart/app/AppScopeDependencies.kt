package com.chart.app

import com.chart.di.DependenciesProvider
import com.chart.points.api.domain.repository.PointRepository
import com.chart.points.impl.data.datasource.PointsLocalDataSourceImpl
import com.chart.points.impl.data.datasource.PointsRemoteDataSource
import com.chart.points.impl.data.mapper.PointsMapperImpl
import com.chart.points.impl.data.repository.PointRepositoryImpl
import com.chart.pointsequenceviewer.api.PointSequenceViewerMediator
import com.chart.pointsequenceviewer.impl.PointSequenceViewerMediatorImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

//TODO: Можно лучше: вынести генерацию зависимости в фичу, а вообще всё это нужно делать через Dagger
object AppScopeDependencies : DependenciesProvider {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://hr-challenge.interactive-ventures.com/api/")
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
        .build()

    private val dependencies: Map<Class<*>, Any> = mapOf(
        PointRepository::class.java to PointRepositoryImpl(
            remoteDataSource = retrofit.create(PointsRemoteDataSource::class.java),
            localDataSource = PointsLocalDataSourceImpl(),
            pointsMapper = PointsMapperImpl()
        ),
        PointSequenceViewerMediator::class.java to PointSequenceViewerMediatorImpl()
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T> provide(clazz: Class<T>): T {
        return dependencies[clazz] as T
    }
}