package com.trusov.sociallab.domain.use_case.usage_stats

import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class CheckUsageStatsPermissionUseCase @Inject constructor(private val repo: Repository) {
    operator fun invoke() = repo.checkUsageStatsPermission()
}