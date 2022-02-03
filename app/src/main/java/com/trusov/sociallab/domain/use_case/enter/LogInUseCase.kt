package com.trusov.sociallab.domain.use_case.enter

import com.trusov.sociallab.common.Constants.USER_IS_NOT_REGISTERED
import com.trusov.sociallab.common.Constants.USER_IS_REGISTERED
import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class LogInUseCase @Inject constructor(private val repo: Repository) {
    operator fun invoke(login: String, password: String): String {
        return try {
            repo.logIn(login, password)
            USER_IS_REGISTERED
        } catch (e: Exception) {
            USER_IS_NOT_REGISTERED
        }
    }
}