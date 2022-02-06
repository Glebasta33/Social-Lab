package com.trusov.sociallab.presentation.fragment.auth.log_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.domain.use_case.auth.GetCurrentRespondentUseCase
import com.trusov.sociallab.domain.use_case.auth.LogInUseCase
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val getCurrentRespondentUseCase: GetCurrentRespondentUseCase
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    suspend fun getCurrentRespondent(): Respondent? {
        return getCurrentRespondentUseCase()
    }

    fun logIn(inputLogin: String?, inputPassword: String?) {
        val login = parseInput(inputLogin)
        val password = parseInput(inputPassword)
        if (validateInput(login, password)) {
            logInUseCase(login, password)
        }
    }

    private fun parseInput(input: String?): String {
        return input?.trim() ?: ""
    }

    private fun validateInput(login: String, password: String): Boolean {
        var result = true
        if (login.isBlank() || password.isBlank()) {
            _message.value = MESSAGE_FILL_INPUTS
            result = false
        }
        return result
    }

    companion object {
        private const val MESSAGE_WELCOME = "Добро пожаловать"
        private const val MESSAGE_WRONG_INPUTS = "Неверный логин или пароль"
        private const val MESSAGE_FILL_INPUTS = "Заполните все поля"
    }
}