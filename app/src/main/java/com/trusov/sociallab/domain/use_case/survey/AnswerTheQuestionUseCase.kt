package com.trusov.sociallab.domain.use_case.survey

import com.trusov.sociallab.domain.entity.Answer
import com.trusov.sociallab.domain.entity.Question
import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class AnswerTheQuestionUseCase @Inject constructor(private val repo: Repository) {
    operator fun invoke(questionId: String, numberOfAnswer: Int) {
        repo.answerTheQuestion(questionId, numberOfAnswer)
    }
}