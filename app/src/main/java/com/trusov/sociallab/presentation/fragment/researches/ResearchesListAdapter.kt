package com.trusov.sociallab.presentation.fragment.researches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.trusov.sociallab.R
import com.trusov.sociallab.domain.entity.Research
import javax.inject.Inject

class ResearchesListAdapter @Inject constructor(
    private val auth: FirebaseAuth
) : ListAdapter<Research, ResearchViewHolder>(ResearchesDiffUtils()) {

    var onResearchItemClickListener: ((Research) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].respondents.contains(auth.currentUser?.uid)) {
            RESEARCH_REGISTERED
        } else {
            RESEARCH_UNREGISTERED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResearchViewHolder {
        val layout = when(viewType) {
            RESEARCH_UNREGISTERED -> R.layout.research_rv_item_layout
            RESEARCH_REGISTERED -> R.layout.research_rv_item_layout_registered
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ResearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResearchViewHolder, position: Int) {
        val research = currentList[position]
        with(holder.binding) {
            tvResearchTopic.text = research.topic
            tvResearchDescription.text = research.description
            tvSelection.text = research.respondents.size.toString()
            root.setOnClickListener {
                onResearchItemClickListener?.invoke(research)
            }
        }
    }

    companion object {
        private const val RESEARCH_UNREGISTERED = 400
        private const val RESEARCH_REGISTERED = 200
    }

}