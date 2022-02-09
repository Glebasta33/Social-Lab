package com.trusov.sociallab.domain.use_case.researches

import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class RegisterToResearchUseCase @Inject constructor(private val repo: Repository) {
    operator fun invoke(researchId: String) {
        repo.registerToResearch(researchId)
    }
}