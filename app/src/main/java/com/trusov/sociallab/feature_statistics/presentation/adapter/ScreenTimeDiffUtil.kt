package com.trusov.sociallab.feature_statistics.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.trusov.sociallab.feature_statistics.domain.entity.AppScreenTime

class ScreenTimeDiffUtil : DiffUtil.ItemCallback<AppScreenTime>() {
    override fun areItemsTheSame(oldItem: AppScreenTime, newItem: AppScreenTime): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AppScreenTime, newItem: AppScreenTime): Boolean {
        return oldItem == newItem
    }
}