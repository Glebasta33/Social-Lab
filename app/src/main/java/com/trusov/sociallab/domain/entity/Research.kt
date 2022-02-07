package com.trusov.sociallab.domain.entity

data class Research(
    val topic: String,
    val description: String,
    val questions: List<Question> = mutableListOf(),
    val respondents: List<Respondent> = mutableListOf()
)
