package com.trusov.sociallab.researches.presentation

import androidx.recyclerview.widget.DiffUtil
import com.trusov.sociallab.researches.domain.entity.Research

class ResearchesDiffUtils : DiffUtil.ItemCallback<Research>() {
    override fun areItemsTheSame(oldItem: Research, newItem: Research): Boolean {
        return oldItem.topic == newItem.topic
    }

    override fun areContentsTheSame(oldItem: Research, newItem: Research): Boolean {
        return oldItem == newItem
    }
}