package com.trusov.sociallab.domain.entity

data class AnswerExtended(
    val questionId: String,
    val respondentId: String,
    val numberOfAnswer: Int,
    val researchTitle: String,
    val textOfQuestion: String,
    val time: String = "17:03 - 12.04.2022"
)
