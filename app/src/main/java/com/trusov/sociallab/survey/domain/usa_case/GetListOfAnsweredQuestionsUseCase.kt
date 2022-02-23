package com.trusov.sociallab.survey.domain.usa_case

import com.trusov.sociallab.survey.domain.entity.AnswerExtended
import com.trusov.sociallab.survey.domain.repository.SurveyRepository
import javax.inject.Inject

class GetListOfAnsweredQuestionsUseCase @Inject constructor(private val repo: SurveyRepository) {
    suspend operator fun invoke(): List<AnswerExtended> = repo.getListOfAnsweredQuestions()
}