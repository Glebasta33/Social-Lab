package com.trusov.sociallab.presentation.fragment.auth.log_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.domain.use_case.auth.GetCurrentRespondentUseCase
import com.trusov.sociallab.domain.use_case.auth.LogInUseCase
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _readyToClose = MutableLiveData<Boolean>()
    val readyToClose: LiveData<Boolean> = _readyToClose

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
        if (login.isBlank() || password.isBlank()) {
            _message.value = MESSAGE_FILL_INPUTS
            return false
        }
        _readyToClose.value = true
        return true
    }

    fun showWrongInputsMessage() {
        _message.value = MESSAGE_WRONG_INPUTS
    }

    companion object {
        private const val MESSAGE_WRONG_INPUTS = "Неверный логин или пароль"
        private const val MESSAGE_FILL_INPUTS = "Заполните все поля"
    }
}