package com.trusov.sociallab.presentation.fragment.research_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trusov.sociallab.domain.entity.Research
import com.trusov.sociallab.domain.use_case.researches.GetResearchByIdUseCase
import javax.inject.Inject

class ResearchInfoViewModel @Inject constructor(
    private val getResearchByIdUseCase: GetResearchByIdUseCase
) : ViewModel() {
    fun getResearchId(researchId: String): LiveData<Research> {
        return getResearchByIdUseCase(researchId)
    }
}