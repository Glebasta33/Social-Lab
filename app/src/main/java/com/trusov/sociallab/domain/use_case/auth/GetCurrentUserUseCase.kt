package com.trusov.sociallab.domain.use_case.auth

import com.google.firebase.auth.FirebaseUser
import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val repo: Repository) {
    suspend operator fun invoke(): FirebaseUser? {
        return repo.getCurrentUser()
    }
}