package com.trusov.sociallab.domain.use_case.researches

import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class GetResearchByIdUseCase @Inject constructor(private val repo: Repository) {
    operator fun invoke(researchId: String) = repo.getResearchById(researchId)
}