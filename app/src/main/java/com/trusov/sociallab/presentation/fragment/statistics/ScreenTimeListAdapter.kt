package com.trusov.sociallab.presentation.fragment.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.trusov.sociallab.R
import com.trusov.sociallab.domain.entity.ScreenTime
import javax.inject.Inject

class ScreenTimeListAdapter @Inject constructor() :
    ListAdapter<ScreenTime, ScreenTimeViewHolder>(ScreenTimeDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenTimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.screen_time_rv_item_layout,
            parent,
            false
        )
        return ScreenTimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScreenTimeViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}