package com.trusov.sociallab.survey.domain.entity

data class AnswerExtended(
    val questionId: String,
    val respondentId: String,
    val numberOfAnswer: Int,
    val researchTitle: String,
    val textOfQuestion: String,
    val createdTime: String = "time",
    val createdDate: String = "date"
)
