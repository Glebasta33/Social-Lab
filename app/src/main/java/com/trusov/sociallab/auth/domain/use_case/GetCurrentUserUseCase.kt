package com.trusov.sociallab.auth.domain.use_case

import com.google.firebase.auth.FirebaseUser
import com.trusov.sociallab.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(): FirebaseUser? {
        return repo.getCurrentUser()
    }
}