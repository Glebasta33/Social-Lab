package com.trusov.sociallab.presentation.fragment.statistics

import androidx.recyclerview.widget.DiffUtil
import com.trusov.sociallab.domain.entity.AppScreenTime

class ScreenTimeDiffUtil : DiffUtil.ItemCallback<AppScreenTime>() {
    override fun areItemsTheSame(oldItem: AppScreenTime, newItem: AppScreenTime): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AppScreenTime, newItem: AppScreenTime): Boolean {
        return oldItem == newItem
    }
}