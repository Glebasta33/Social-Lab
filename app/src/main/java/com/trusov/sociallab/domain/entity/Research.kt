package com.trusov.sociallab.domain.entity

data class Research(
    val topic: String,
    val description: String,
    val questions: List<Question>,
    val respondents: List<Respondent>
)
