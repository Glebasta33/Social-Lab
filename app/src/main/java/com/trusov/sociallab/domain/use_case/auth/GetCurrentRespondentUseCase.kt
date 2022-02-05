package com.trusov.sociallab.domain.use_case.auth

import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class GetCurrentRespondentUseCase @Inject constructor(private val repo: Repository) {
    suspend operator fun invoke(): Respondent? {
        return repo.getCurrentRespondent()
    }
}