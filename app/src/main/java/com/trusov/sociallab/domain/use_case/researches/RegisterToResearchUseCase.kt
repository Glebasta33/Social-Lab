package com.trusov.sociallab.domain.use_case.researches

import com.trusov.sociallab.domain.repository.Repository

class RegisterToResearchUseCase(private val repo: Repository) {
    operator fun invoke(respondentId: Long, researchId: Long) {
        repo.registerToResearch(respondentId, researchId)
    }
}