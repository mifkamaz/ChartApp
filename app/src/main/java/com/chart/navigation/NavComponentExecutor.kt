package com.chart.navigation

import androidx.navigation.NavController
import java.util.LinkedList
import java.util.Queue

class NavControllerExecutor {

    private var navController: NavController? = null

    private val navigationQueue: Queue<NavController.() -> Unit> = LinkedList()

    fun execute(command: NavController.() -> Unit) {
        navController?.apply(command) ?: navigationQueue.add(command)
    }

    fun bind(fragment: NavController) {
        this.navController = fragment
        while (!navigationQueue.isEmpty()) {
            @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            navigationQueue.poll().invoke(fragment)
        }
    }

    fun unbind() {
        navController = null
    }
}