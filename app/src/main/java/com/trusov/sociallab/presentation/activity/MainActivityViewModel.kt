package com.trusov.sociallab.presentation.activity

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.trusov.sociallab.auth.domain.use_case.GetCurrentUserUseCase
import com.trusov.sociallab.auth.domain.use_case.SignOutUseCase
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    suspend fun getCurrentUser(): FirebaseUser? {
        return getCurrentUserUseCase()
    }

    fun signOut() {
        signOutUseCase()
    }
}