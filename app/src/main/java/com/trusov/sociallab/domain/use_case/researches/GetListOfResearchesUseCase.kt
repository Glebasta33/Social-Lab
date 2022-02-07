package com.trusov.sociallab.domain.use_case.researches

import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class GetListOfResearchesUseCase @Inject constructor(private val repo: Repository) {
    operator fun invoke() = repo.getListOfResearches()
}