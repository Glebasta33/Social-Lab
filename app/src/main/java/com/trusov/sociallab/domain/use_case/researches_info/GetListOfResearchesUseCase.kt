package com.trusov.sociallab.domain.use_case.researches_info

import com.trusov.sociallab.domain.repository.Repository

class GetListOfResearchesUseCase(private val repo: Repository) {
    operator fun invoke() = repo.getListOfResearches()
}