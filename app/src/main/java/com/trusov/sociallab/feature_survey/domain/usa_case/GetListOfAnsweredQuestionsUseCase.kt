package com.trusov.sociallab.feature_survey.domain.usa_case

import com.trusov.sociallab.feature_survey.domain.entity.AnswerExtended
import com.trusov.sociallab.feature_survey.domain.repository.SurveyRepository
import javax.inject.Inject

class GetListOfAnsweredQuestionsUseCase @Inject constructor(private val repo: SurveyRepository) {
    suspend operator fun invoke(): List<AnswerExtended> = repo.getListOfAnsweredQuestions()
}