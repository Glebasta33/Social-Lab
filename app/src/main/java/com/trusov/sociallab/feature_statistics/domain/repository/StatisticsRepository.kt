package com.trusov.sociallab.feature_statistics.domain.repository

import com.trusov.sociallab.feature_statistics.domain.entity.AppScreenTime

interface StatisticsRepository {
    fun getListOfScreenTime(): List<AppScreenTime>
    fun getTotalScreenTime(): AppScreenTime
    fun checkUsageStatsPermission(): Boolean
}