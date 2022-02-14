package com.trusov.sociallab.domain.entity

data class Answer(
    val questionId: String,
    val respondentId: String,
    val numberOfAnswer: Int
)
