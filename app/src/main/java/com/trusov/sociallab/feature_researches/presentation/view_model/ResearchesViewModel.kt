package com.trusov.sociallab.feature_researches.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trusov.sociallab.feature_researches.domain.entity.Research
import com.trusov.sociallab.feature_researches.domain.usa_case.GetListOfResearchesUseCase
import javax.inject.Inject

class ResearchesViewModel @Inject constructor(
    private val getListOfResearchesUseCase: GetListOfResearchesUseCase
) : ViewModel() {

    fun getListOfResearches(): LiveData<List<Research>> {
        return getListOfResearchesUseCase()
    }
}