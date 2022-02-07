package com.trusov.sociallab.presentation.fragment.researches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trusov.sociallab.domain.entity.Research
import com.trusov.sociallab.domain.use_case.researches.GetListOfResearchesUseCase
import javax.inject.Inject

class ResearchesViewModel @Inject constructor(
    private val getListOfResearchesUseCase: GetListOfResearchesUseCase
) : ViewModel() {

    fun getListOfResearches(): LiveData<List<Research>> {
        return getListOfResearchesUseCase()
    }
}