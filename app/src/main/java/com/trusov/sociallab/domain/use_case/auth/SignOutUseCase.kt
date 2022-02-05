package com.trusov.sociallab.domain.use_case.auth

import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val repo: Repository) {
    operator fun invoke() {
        repo.signOut()
    }
}