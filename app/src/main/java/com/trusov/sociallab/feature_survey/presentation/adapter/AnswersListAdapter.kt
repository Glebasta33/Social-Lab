package com.trusov.sociallab.feature_survey.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.trusov.sociallab.R
import com.trusov.sociallab.feature_survey.domain.entity.AnswerExtended
import javax.inject.Inject

class AnswersListAdapter @Inject constructor() :
    ListAdapter<AnswerExtended, AnswerViewHolder>(AnswersDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.answer_rv_item_layout,
            parent,
            false
        )
        return AnswerViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val answer = currentList[position]
        with(holder.binding) {
            tvNumberOfAnswer.text = answer.numberOfAnswer.toString()
            tvResearchTitle.text = answer.researchTitle
            tvQuestion.text = answer.textOfQuestion
            tvTimeOfAnswer.text = answer.createdTime
            tvDateOfAnswer.text =  answer.createdDate
        }
    }
}