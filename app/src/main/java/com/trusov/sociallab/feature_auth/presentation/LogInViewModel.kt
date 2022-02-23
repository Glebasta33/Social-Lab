package com.trusov.sociallab.feature_auth.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.trusov.sociallab.feature_auth.domain.use_case.GetCurrentUserUseCase
import com.trusov.sociallab.feature_auth.domain.use_case.LogInUseCase
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logInUseCase: LogInUseCase
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _readyToClose = MutableLiveData<Boolean>()
    val readyToClose: LiveData<Boolean> = _readyToClose

    suspend fun getCurrentUser(): FirebaseUser? {
        return getCurrentUserUseCase()
    }

    fun logIn(inputLogin: String?, inputPassword: String?) {
        fun parseInput(input: String?): String {
            return input?.trim() ?: ""
        }

        fun validateInput(login: String, password: String): Boolean {
            if (login.isBlank() || password.isBlank()) {
                _message.value = MESSAGE_FILL_INPUTS
                return false
            }
            _readyToClose.value = true
            return true
        }

        val login = parseInput(inputLogin)
        val password = parseInput(inputPassword)
        if (validateInput(login, password)) {
            logInUseCase(login, password)
        }
    }

    fun showWrongInputsMessage() {
        _message.value = MESSAGE_WRONG_INPUTS
    }

    companion object {
        private const val MESSAGE_WRONG_INPUTS = "Неверный логин или пароль"
        private const val MESSAGE_FILL_INPUTS = "Заполните все поля"
    }
}