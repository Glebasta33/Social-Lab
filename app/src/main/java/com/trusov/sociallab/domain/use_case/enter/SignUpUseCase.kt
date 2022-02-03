package com.trusov.sociallab.domain.use_case.enter

import com.trusov.sociallab.common.Constants.PASSWORD_ARE_NOT_SAME
import com.trusov.sociallab.common.Constants.PASSWORD_ARE_SAME
import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repo: Repository) {
    operator fun invoke(login: String, password: String) {
            repo.signUp(login, password)
    }
}