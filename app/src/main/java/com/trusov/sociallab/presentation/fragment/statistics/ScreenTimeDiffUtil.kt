package com.trusov.sociallab.presentation.fragment.statistics

import androidx.recyclerview.widget.DiffUtil
import com.trusov.sociallab.domain.entity.ScreenTime

class ScreenTimeDiffUtil : DiffUtil.ItemCallback<ScreenTime>() {
    override fun areItemsTheSame(oldItem: ScreenTime, newItem: ScreenTime): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ScreenTime, newItem: ScreenTime): Boolean {
        return oldItem == newItem
    }
}