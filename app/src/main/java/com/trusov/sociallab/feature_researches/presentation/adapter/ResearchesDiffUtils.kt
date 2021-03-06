package com.trusov.sociallab.feature_researches.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.trusov.sociallab.feature_researches.domain.entity.Research

class ResearchesDiffUtils : DiffUtil.ItemCallback<Research>() {
    override fun areItemsTheSame(oldItem: Research, newItem: Research): Boolean {
        return oldItem.topic == newItem.topic
    }

    override fun areContentsTheSame(oldItem: Research, newItem: Research): Boolean {
        return oldItem == newItem
    }
}