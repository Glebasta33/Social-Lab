package com.trusov.sociallab.feature_survey.domain.use_case

import com.trusov.sociallab.feature_survey.domain.repository.SurveyRepository
import javax.inject.Inject

class GetQuestionUseCase @Inject constructor(private val repo: SurveyRepository) {
    suspend operator fun invoke() {
        return repo.getQuestion()
    }
}