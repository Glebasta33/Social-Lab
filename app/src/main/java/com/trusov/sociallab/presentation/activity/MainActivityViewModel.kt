package com.trusov.sociallab.presentation.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.domain.use_case.auth.GetCurrentRespondentUseCase
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val getCurrentRespondentUseCase: GetCurrentRespondentUseCase
) : ViewModel() {

    suspend fun getCurrentRespondent(): Respondent? {
        return getCurrentRespondentUseCase()
    }
}