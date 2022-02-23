package com.trusov.sociallab.domain.repository

import com.trusov.sociallab.domain.entity.AppScreenTime

interface Repository {
    // respondent info
    fun getListOfScreenTime(): List<AppScreenTime>
    fun getTotalScreenTime(): AppScreenTime
    fun checkUsageStatsPermission(): Boolean
}