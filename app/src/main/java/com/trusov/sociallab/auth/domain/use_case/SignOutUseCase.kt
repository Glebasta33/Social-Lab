package com.trusov.sociallab.auth.domain.use_case

import com.trusov.sociallab.auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val repo: AuthRepository) {
    operator fun invoke() {
        repo.signOut()
    }
}