package com.trusov.sociallab.domain.use_case.respondent_info

import com.trusov.sociallab.domain.repository.Repository

class GetUserStatisticsUseCase(private val repo: Repository) {
    operator fun invoke(respondentId: Long) = repo.getUserStatistics(respondentId)
}