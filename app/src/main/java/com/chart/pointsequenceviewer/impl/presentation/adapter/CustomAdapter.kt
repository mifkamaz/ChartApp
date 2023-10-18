package com.chart.pointsequenceviewer.impl.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chart.R
import com.chart.databinding.ItemPointBinding
import com.chart.points.api.domain.model.Point

class CustomAdapter(private val points: List<Point>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemPointBinding = ItemPointBinding.bind(view)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_point, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (position == 0) {
            viewHolder.binding.xTextView.text = "X"
            viewHolder.binding.yTextView.text = "Y"
        } else {
            val point = points[position - 1]
            viewHolder.binding.xTextView.text = point.x.toString()
            viewHolder.binding.yTextView.text = point.y.toString()
        }
    }

    override fun getItemCount() = points.size + 1

}