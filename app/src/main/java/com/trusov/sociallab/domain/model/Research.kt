package com.trusov.sociallab.domain.model

data class Research(
    private val topic: String,
    private val description: String,
    private val questions: List<Question>,
    private val respondents: List<Respondent>
)
