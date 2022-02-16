package com.trusov.sociallab.presentation.fragment.answers

import androidx.recyclerview.widget.DiffUtil
import com.trusov.sociallab.domain.entity.AnswerExtended

class AnswersDiffUtil : DiffUtil.ItemCallback<AnswerExtended>() {
    override fun areItemsTheSame(oldItem: AnswerExtended, newItem: AnswerExtended): Boolean {
        return oldItem.respondentId == newItem.respondentId && oldItem.questionId == newItem.questionId
    }

    override fun areContentsTheSame(oldItem: AnswerExtended, newItem: AnswerExtended): Boolean {
        return oldItem == newItem
    }
}