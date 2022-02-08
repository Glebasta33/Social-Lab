package com.trusov.sociallab.presentation.fragment.researches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.trusov.sociallab.R
import com.trusov.sociallab.domain.entity.Research

class ResearchesListAdapter : ListAdapter<Research, ResearchViewHolder>(ResearchesDiffUtils()) {

    var onResearchItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.research_rv_item_layout,
            parent,
            false
        )
        return ResearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResearchViewHolder, position: Int) {
        val research = currentList[position]
        with(holder.binding) {
            tvResearchTopic.text = research.topic
            tvResearchDescription.text = research.description
            root.setOnClickListener {
                onResearchItemClickListener?.invoke(research.id)
            }
        }
    }
}