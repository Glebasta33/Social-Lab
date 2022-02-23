package com.trusov.sociallab.survey.domain.usa_case

import com.trusov.sociallab.survey.domain.repository.SurveyRepository
import javax.inject.Inject

class GetQuestionUseCase @Inject constructor(private val repo: SurveyRepository) {
    operator fun invoke() {
        return repo.getQuestion()
    }
}