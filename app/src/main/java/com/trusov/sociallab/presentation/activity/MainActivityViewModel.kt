package com.trusov.sociallab.presentation.activity

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.trusov.sociallab.domain.use_case.auth.GetCurrentUserUseCase
import com.trusov.sociallab.domain.use_case.auth.SignOutUseCase
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