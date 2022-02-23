package com.trusov.sociallab.feature_researches.domain.usa_case

import com.trusov.sociallab.feature_researches.domain.repository.ResearchesRepository
import javax.inject.Inject

class GetResearchByIdUseCase @Inject constructor(private val repo: ResearchesRepository) {
    operator fun invoke(researchId: String) = repo.getResearchById(researchId)
}