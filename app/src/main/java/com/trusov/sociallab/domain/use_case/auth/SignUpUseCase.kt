package com.trusov.sociallab.domain.use_case.auth

import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repo: Repository) {
    operator fun invoke(login: String, password: String) {
            repo.signUp(login, password)
    }
}