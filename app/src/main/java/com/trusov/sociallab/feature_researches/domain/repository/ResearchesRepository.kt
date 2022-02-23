package com.trusov.sociallab.feature_researches.domain.repository

import androidx.lifecycle.LiveData
import com.trusov.sociallab.feature_researches.domain.entity.Research

interface ResearchesRepository {
    fun getListOfResearches(): LiveData<List<Research>>
    fun getResearchById(researchId: String): LiveData<Research>
    fun registerToResearch(researchId: String)
    fun unregisterFromResearch(researchId: String)
}