package com.trusov.sociallab.domain.entity

data class Research(
    val topic: String,
    val description: String,
    val questions: List<Question> = mutableListOf(),
    val respondents: List<Respondent> = mutableListOf(),
    val id: String = UNDEFINED_ID
) {
    companion object {
        private const val UNDEFINED_ID = ""
    }
}
