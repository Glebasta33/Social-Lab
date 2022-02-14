package com.trusov.sociallab.domain.use_case.survey

import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class GetQuestionUseCase @Inject constructor(private val repo: Repository) {
    operator fun invoke() {
        return repo.getQuestion()
    }
}