package com.trusov.sociallab.feature_statistics.data.repository

import com.trusov.sociallab.di.scope.ApplicationScope
import com.trusov.sociallab.feature_statistics.data.source.UStats
import com.trusov.sociallab.feature_statistics.domain.entity.AppScreenTime
import com.trusov.sociallab.feature_statistics.domain.repository.StatisticsRepository
import javax.inject.Inject

@ApplicationScope
class StatisticsRepositoryImpl @Inject constructor(
    private val usageStats: UStats
) : StatisticsRepository {

    override fun getListOfScreenTime(): List<AppScreenTime> {
        return usageStats.getListOfScreenTime()
    }

    override fun getTotalScreenTime(): AppScreenTime {
        return usageStats.getTotalScreenTime()
    }

    override fun checkUsageStatsPermission(): Boolean {
        return usageStats.getUsageStatsList().isEmpty()
    }
}