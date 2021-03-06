package com.trusov.sociallab.feature_survey.domain.entity

data class Question(
    val text: String,
    val id: String,
    val researchId: String,
    val type: QuestionType,
    val timeScope: Pair<String, String>?,
    val periodInMinutes: Int?
)
