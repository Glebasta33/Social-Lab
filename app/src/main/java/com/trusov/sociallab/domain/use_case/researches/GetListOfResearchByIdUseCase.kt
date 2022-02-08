package com.trusov.sociallab.domain.use_case.researches

import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class GetListOfResearchByIdUseCase @Inject constructor(private val repo: Repository) {
    operator fun invoke(respondentId: String) = repo.getListOfResearchById(respondentId)
}