package com.trusov.sociallab.feature_auth.domain.use_case

import com.trusov.sociallab.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repo: AuthRepository) {
    operator fun invoke(login: String, password: String) {
            repo.signUp(login, password)
    }
}