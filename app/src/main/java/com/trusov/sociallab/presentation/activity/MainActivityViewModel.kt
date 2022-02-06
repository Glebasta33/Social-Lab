package com.trusov.sociallab.presentation.activity

import androidx.lifecycle.ViewModel
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.domain.use_case.auth.GetCurrentRespondentUseCase
import com.trusov.sociallab.domain.use_case.auth.SignOutUseCase
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val getCurrentRespondentUseCase: GetCurrentRespondentUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    suspend fun getCurrentRespondent(): Respondent? {
        return getCurrentRespondentUseCase()
    }

    fun signOut() {
        signOutUseCase()
    }
}