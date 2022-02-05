package com.trusov.sociallab.presentation.fragment.researches

import androidx.lifecycle.ViewModel
import com.trusov.sociallab.domain.use_case.auth.SignOutUseCase
import javax.inject.Inject

class ResearchesViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {
    fun signOut() {
        signOutUseCase()
    }
}