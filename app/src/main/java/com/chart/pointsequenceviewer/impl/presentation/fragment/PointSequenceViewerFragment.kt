package com.chart.pointsequenceviewer.impl.presentation.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chart.R
import com.chart.databinding.FragmnentPointSequenceViewerBinding
import com.chart.di.findDependency
import com.chart.points.api.domain.model.PointSequenceId
import com.chart.points.api.domain.repository.PointRepository
import com.chart.pointsequenceviewer.impl.presentation.adapter.CustomAdapter
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf

//TODO: Не стал заморачиваться тут c VM
class PointSequenceViewerFragment : Fragment() {

    private val binding by lazy {
        FragmnentPointSequenceViewerBinding.inflate(layoutInflater)
    }
    private val id: PointSequenceId by lazy {
        PointSequenceId(arguments?.getString("id").orEmpty())
    }
    private val repo: PointRepository by lazy { findDependency() }
    private val sequence by lazy { repo.getPointSequence(id) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderChart()
        renderTable()
    }

    private fun renderChart() {
        val listOf = sequence.points.map { FloatEntry(it.x, it.y) }
        binding.chartView.setModel(
            entryModelOf(listOf)
        )
    }

    private fun renderTable() {
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
        binding.recyclerView.adapter = CustomAdapter(sequence.points)
    }
}