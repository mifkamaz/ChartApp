package com.chart.app

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.chart.databinding.ActivityMainBinding
import com.chart.di.findDependency
import com.chart.navigation.NavControllerExecutor


class MainActivity : FragmentActivity() {

    private val navControllerExecutor: NavControllerExecutor by lazy { findDependency() }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        navControllerExecutor.bind(findNavController(binding.fragmentContainerView.id))
    }

    override fun onPause() {
        navControllerExecutor.unbind()
        super.onPause()
    }

}