package com.chart.points.impl.data.repository

import com.chart.network.domain.NetworkError
import com.chart.points.api.domain.model.BusinessError
import com.chart.points.api.domain.model.InputError
import com.chart.points.api.domain.model.PointSequence
import com.chart.points.api.domain.model.PointSequenceId
import com.chart.points.api.domain.repository.PointRepository
import com.chart.points.impl.data.datasource.PointsLocalDataSource
import com.chart.points.impl.data.datasource.PointsRemoteDataSource
import com.chart.points.impl.data.mapper.PointsMapper
import retrofit2.HttpException

//TODO: Зачем здесь локальное хранилище? См. комент к методу PointSequenceGeneratorNavigationImpl::openPointSequenceViewer
class PointRepositoryImpl(
    private val remoteDataSource: PointsRemoteDataSource,
    private val localDataSource: PointsLocalDataSource,
    private val pointsMapper: PointsMapper
) : PointRepository {

    override suspend fun generatePointSequence(count: Int): PointSequence {
        val response = try {
            remoteDataSource.generatePoints(count)
        } catch (ex: Throwable) {
            //TODO: Подумать над обёрткой для маппинга ошибок, что-бы не дублировать код
            throwIfBusinessError(ex)
            throw NetworkError(ex)
        }
        val points = pointsMapper.mapGeneratePointsError(response.points)
            //TODO: Сортировка это бизнес логика.
            //      Я бы попросил бекендеров присылать отсортированный массив
            //      либо, сортировал в VM фичи с графиком, если порядок имеет сокральный смысл
            .sortedBy { it.x }
        return localDataSource.save(points)
    }

    private fun throwIfBusinessError(ex: Throwable) {
        if (ex is HttpException) {
            throw when (ex.code()) {
                500 -> BusinessError(ex.response()?.errorBody()?.string().orEmpty(), ex)
                400 -> InputError(ex)
                else -> NetworkError(ex)
            }
        }
    }

    override fun getPointSequence(id: PointSequenceId): PointSequence {
        return localDataSource.get(id)
    }
}