package com.trusov.sociallab.presentation.fragment.log_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.domain.use_case.enter.LogInUseCase
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase
) : ViewModel() {

    private val _respondent = MutableLiveData<Respondent>()
    val respondent: LiveData<Respondent> = _respondent

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun logIn(inputLogin: String?, inputPassword: String?) {
        val login = parseInput(inputLogin)
        val password = parseInput(inputPassword)
        if (validateInput(login, password)) {
            val respondent = logInUseCase(login, password)
            if (respondent != null) {
                _message.value = MESSAGE_WELCOME
                _respondent.value = respondent!!
            } else {
                _message.value = MESSAGE_WRONG_INPUTS
            }
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