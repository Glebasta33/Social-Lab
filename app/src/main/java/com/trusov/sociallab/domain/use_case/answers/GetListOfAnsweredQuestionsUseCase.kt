package com.trusov.sociallab.domain.use_case.answers

import com.trusov.sociallab.domain.entity.AnswerExtended
import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class GetListOfAnsweredQuestionsUseCase @Inject constructor(private val repo: Repository) {
    suspend operator fun invoke(): List<AnswerExtended> = repo.getListOfAnsweredQuestions()
}