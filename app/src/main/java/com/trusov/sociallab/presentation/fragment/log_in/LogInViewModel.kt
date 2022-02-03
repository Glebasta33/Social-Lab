package com.trusov.sociallab.presentation.fragment.log_in

import androidx.lifecycle.ViewModel
import com.trusov.sociallab.domain.use_case.enter.LogInUseCase
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase
) : ViewModel() {
    fun logIn(login: String, password: String) {
        logInUseCase(login, password)
    }
}