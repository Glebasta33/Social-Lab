package com.trusov.sociallab.researches.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trusov.sociallab.researches.domain.entity.Research
import com.trusov.sociallab.researches.domain.usa_case.GetListOfResearchesUseCase
import javax.inject.Inject

class ResearchesViewModel @Inject constructor(
    private val getListOfResearchesUseCase: GetListOfResearchesUseCase
) : ViewModel() {

    fun getListOfResearches(): LiveData<List<Research>> {
        return getListOfResearchesUseCase()
    }
}