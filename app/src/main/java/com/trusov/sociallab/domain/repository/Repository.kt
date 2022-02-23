package com.trusov.sociallab.domain.repository

import androidx.lifecycle.LiveData
import com.trusov.sociallab.domain.entity.Research
import com.trusov.sociallab.domain.entity.AppScreenTime

interface Repository {

    // researches info
    fun getListOfResearches(): LiveData<List<Research>>
    fun getListOfResearchById(respondentId: String): LiveData<List<Research>>
    fun getResearchById(researchId: String): LiveData<Research>
    fun registerToResearch(researchId: String)
    fun unregisterFromResearch(researchId: String)

    // survey

    // respondent info
    fun getListOfScreenTime(): List<AppScreenTime>
    fun getTotalScreenTime(): AppScreenTime
    fun checkUsageStatsPermission(): Boolean
}