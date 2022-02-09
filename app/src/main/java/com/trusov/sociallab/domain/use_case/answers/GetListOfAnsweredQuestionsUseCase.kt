package com.trusov.sociallab.domain.use_case.answers

import com.trusov.sociallab.domain.repository.Repository

class GetListOfAnsweredQuestionsUseCase(private val repo: Repository) {
    operator fun invoke(respondentId: String) = repo.getListOfAnsweredQuestions(respondentId)
}