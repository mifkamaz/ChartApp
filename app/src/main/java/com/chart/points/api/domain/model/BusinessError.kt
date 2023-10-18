package com.chart.points.api.domain.model

class BusinessError(message: String, ex: Throwable) : Throwable(message, ex)