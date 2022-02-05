package com.trusov.sociallab.presentation.fragment.sing_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.domain.use_case.auth.GetCurrentRespondentUseCase
import com.trusov.sociallab.domain.use_case.auth.LogInUseCase
import com.trusov.sociallab.domain.use_case.auth.SignUpUseCase
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val singUpUseCase: SignUpUseCase,
    private val getCurrentRespondentUseCase: GetCurrentRespondentUseCase
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _readyToClose = MutableLiveData<Boolean>()
    val readyToClose: LiveData<Boolean> = _readyToClose

    suspend fun getCurrentRespondent(): Respondent? {
        return getCurrentRespondentUseCase()
    }

    fun singUp(
        inputLogin: String?,
        inputPassword1: String?,
        inputPassword2: String?,
        agreeWithTheTerms: Boolean
    ) {
        val login = parseInput(inputLogin)
        val password1 = parseInput(inputPassword1)
        val password2 = parseInput(inputPassword2)
        if (validateInput(login, password1, password2, agreeWithTheTerms)){
            singUpUseCase(login, password1)
        }
    }

    private fun parseInput(input: String?): String {
        return input?.trim() ?: ""
    }

    private fun validateInput(
        login: String,
        password1: String,
        password2: String,
        agreeWithTheTerms: Boolean
    ): Boolean {
        if (login.isBlank() || password1.isBlank() || password2.isBlank()) {
            _message.value = MESSAGE_FILL_INPUTS
            return false
        }
        if (password1 != password2) {
            _message.value = MESSAGE_PASSWORDS_DIFFERS
            return false
        }
        if (password1.length < 6) {
            _message.value = MESSAGE_PASSWORDS_LENGTH
            return false
        }
        if (!agreeWithTheTerms) {
            _message.value = MESSAGE_CONFIRM
            return false
        }
        _readyToClose.value = true
        return true
    }

    companion object {
        private const val MESSAGE_SUCCESS = "Регистрация прошла успешно"
        private const val MESSAGE_FILL_INPUTS = "Заполните все поля"
        private const val MESSAGE_PASSWORDS_DIFFERS = "Введённые пароли отличаются"
        private const val MESSAGE_PASSWORDS_LENGTH = "Пароль должен быть не короче 6 символов"
        private const val MESSAGE_CONFIRM = "Подтвердите согласие с условиями использования"
    }
}