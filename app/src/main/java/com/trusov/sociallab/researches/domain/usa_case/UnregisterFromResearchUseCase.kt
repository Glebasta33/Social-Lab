package com.trusov.sociallab.researches.domain.usa_case

import com.trusov.sociallab.researches.domain.repository.ResearchesRepository
import javax.inject.Inject

class UnregisterFromResearchUseCase @Inject constructor(private val repo: ResearchesRepository)  {
    operator fun invoke(researchId: String) {
        repo.unregisterFromResearch(researchId)
    }
}