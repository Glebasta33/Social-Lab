package com.trusov.sociallab.domain.use_case.researches_info

import com.trusov.sociallab.domain.repository.Repository

class GetResearchByIdUseCase(private val repo: Repository) {
    operator fun invoke(researchId: Long) = repo.getResearchById(researchId)
}