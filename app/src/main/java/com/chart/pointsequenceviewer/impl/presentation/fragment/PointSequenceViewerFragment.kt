package com.chart.pointsequenceviewer.impl.presentation.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chart.R
import com.chart.databinding.FragmnentPointSequenceViewerBinding
import com.chart.pointsequenceviewer.impl.presentation.adapter.CustomAdapter
import com.chart.pointsequenceviewer.impl.presentation.model.mvi.PointSequenceViewer
import com.chart.pointsequenceviewer.impl.presentation.viewmodel.PointSequenceViewerViewModel
import com.chart.pointsequenceviewer.impl.presentation.viewmodelfactory.PointSequenceViewerViewModelFactory
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PointSequenceViewerFragment : Fragment() {

    private val binding by lazy {
        FragmnentPointSequenceViewerBinding.inflate(layoutInflater)
    }
    private val viewModel: PointSequenceViewerViewModel
            by viewModels { PointSequenceViewerViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.onEach { renderState(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun renderState(state: PointSequenceViewer.State) {
        renderChart(state)
        renderTable(state)
    }

    private fun renderChart(state: PointSequenceViewer.State) {
        val listOf = state.points.map { FloatEntry(it.x, it.y) }
        binding.chartView.setModel(
            entryModelOf(listOf)
        )
    }

    private fun renderTable(state: PointSequenceViewer.State) {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        val materialDividerItemDecoration =
            MaterialDividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL)
        materialDividerItemDecoration.dividerColor = Color.WHITE
        materialDividerItemDecoration.setDividerThicknessResource(
            requireContext(),
            R.dimen.item_diver_thickness
        )
        binding.recyclerView.addItemDecoration(materialDividerItemDecoration)
        binding.recyclerView.adapter = CustomAdapter(state.points)
    }
}