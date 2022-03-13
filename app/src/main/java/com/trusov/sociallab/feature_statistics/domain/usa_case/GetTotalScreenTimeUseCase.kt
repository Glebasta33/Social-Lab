package com.trusov.sociallab.feature_statistics.domain.usa_case

import com.trusov.sociallab.feature_statistics.domain.repository.StatisticsRepository
import javax.inject.Inject

class GetTotalScreenTimeUseCase @Inject constructor(private val repo: StatisticsRepository) {
    operator fun invoke() = repo.getTotalScreenTime()
}