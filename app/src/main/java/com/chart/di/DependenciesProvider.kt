package com.chart.di

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment

interface DependenciesProvider {

    fun <T> provide(clazz: Class<T>): T

}

inline fun <reified T> Fragment.findDependency(): T {
    return requireActivity().findDependency()
}

inline fun <reified T> Activity.findDependency(): T {
    return application.findDependency()
}

inline fun <reified T> Application.findDependency(): T {
    val dependenciesProvider = this as DependenciesProvider
    return dependenciesProvider.provide(T::class.java)
}