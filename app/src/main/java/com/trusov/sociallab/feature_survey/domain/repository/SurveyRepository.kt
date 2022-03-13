package com.trusov.sociallab.feature_survey.domain.repository

import com.trusov.sociallab.feature_survey.domain.entity.AnswerExtended

interface SurveyRepository {
    fun getQuestion()
    fun answerTheQuestion(questionId: String, numberOfAnswer: Int)
    suspend fun getListOfAnsweredQuestions(): List<AnswerExtended>
}