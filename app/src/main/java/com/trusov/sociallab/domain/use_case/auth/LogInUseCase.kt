package com.trusov.sociallab.domain.use_case.auth

import android.util.Log
import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

class LogInUseCase @Inject constructor(private val repo: Repository) {
    operator fun invoke(login: String, password: String) {
        try {
            repo.logIn(login, password)
        } catch (e: Exception) {
            Log.d("LogcatDebug", "Ошибка в $this: ${e.message}")

        }
    }
}