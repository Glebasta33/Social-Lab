package com.trusov.sociallab.feature_survey.domain.usa_case

import com.trusov.sociallab.feature_survey.domain.repository.SurveyRepository
import javax.inject.Inject

class AnswerTheQuestionUseCase @Inject constructor(private val repo: SurveyRepository) {
    operator fun invoke(questionId: String, numberOfAnswer: Int) {
        repo.answerTheQuestion(questionId, numberOfAnswer)
    }
}