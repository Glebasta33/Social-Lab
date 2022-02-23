package com.trusov.sociallab.survey.domain.usa_case

import com.trusov.sociallab.survey.domain.repository.SurveyRepository
import javax.inject.Inject

class AnswerTheQuestionUseCase @Inject constructor(private val repo: SurveyRepository) {
    operator fun invoke(questionId: String, numberOfAnswer: Int) {
        repo.answerTheQuestion(questionId, numberOfAnswer)
    }
}