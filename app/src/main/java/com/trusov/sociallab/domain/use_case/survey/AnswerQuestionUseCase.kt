package com.trusov.sociallab.domain.use_case.survey

import com.trusov.sociallab.domain.entity.Question
import com.trusov.sociallab.domain.repository.Repository

class AnswerQuestionUseCase(private val repo: Repository) {
    operator fun invoke(question: Question) {
        repo.answerQuestion(question)
    }
}