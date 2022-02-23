package com.trusov.sociallab.auth.domain.use_case

import android.util.Log
import com.trusov.sociallab.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(private val repo: AuthRepository) {
    operator fun invoke(login: String, password: String) {
        try {
            repo.logIn(login, password)
        } catch (e: Exception) {
            Log.d("LogcatDebug", "Ошибка в $this: ${e.message}")

        }
    }
}