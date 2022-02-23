package com.trusov.sociallab.presentation.fragment.research_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.trusov.sociallab.domain.entity.Research
import com.trusov.sociallab.auth.domain.use_case.GetCurrentUserUseCase
import com.trusov.sociallab.domain.use_case.researches.GetResearchByIdUseCase
import com.trusov.sociallab.domain.use_case.researches.RegisterToResearchUseCase
import com.trusov.sociallab.domain.use_case.researches.UnregisterFromResearchUseCase
import javax.inject.Inject

class ResearchInfoViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getResearchByIdUseCase: GetResearchByIdUseCase,
    private val registerToResearchUseCase: RegisterToResearchUseCase,
    private val unregisterFromResearchUseCase: UnregisterFromResearchUseCase
) : ViewModel() {

    suspend fun getCurrentUser(): FirebaseUser? {
        return getCurrentUserUseCase()
    }

    fun getResearchId(researchId: String): LiveData<Research> {
        return getResearchByIdUseCase(researchId)
    }

    fun registerToResearch(researchId: String) {
        registerToResearchUseCase(researchId)
    }

    fun unregisterFromResearch(researchId: String) {
        unregisterFromResearchUseCase(researchId)
    }
}