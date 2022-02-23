package com.trusov.sociallab.survey.domain.repository

import com.trusov.sociallab.survey.domain.entity.AnswerExtended

interface SurveyRepository {
    fun getQuestion()
    fun answerTheQuestion(questionId: String, numberOfAnswer: Int)
    suspend fun getListOfAnsweredQuestions(): List<AnswerExtended>
}