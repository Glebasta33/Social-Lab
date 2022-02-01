package com.trusov.sociallab.domain.use_case.researches_info

import com.trusov.sociallab.domain.repository.Repository

class GetListOfResearchByIdUseCase(private val repo: Repository) {
    operator fun invoke(respondentId: Long) = repo.getListOfResearchById(respondentId)
}