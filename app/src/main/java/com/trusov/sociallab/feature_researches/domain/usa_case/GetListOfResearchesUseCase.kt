package com.trusov.sociallab.feature_researches.domain.usa_case

import com.trusov.sociallab.feature_researches.domain.repository.ResearchesRepository
import javax.inject.Inject

class GetListOfResearchesUseCase @Inject constructor(private val repo: ResearchesRepository) {
    operator fun invoke() = repo.getListOfResearches()
}