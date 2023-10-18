package com.chart.app

import android.app.Application
import com.chart.di.DependenciesProvider
import timber.log.Timber
import timber.log.Timber.DebugTree


class ChartApp : Application(), DependenciesProvider by AppScopeDependencies {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}