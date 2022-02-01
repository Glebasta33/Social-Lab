package com.trusov.sociallab.domain.use_case.survey

import com.trusov.sociallab.domain.model.Question
import com.trusov.sociallab.domain.repository.Repository

class GetQuestionUseCase(private val repo: Repository) {
    operator fun invoke(): Question {
        return repo.getQuestion()
    }
}