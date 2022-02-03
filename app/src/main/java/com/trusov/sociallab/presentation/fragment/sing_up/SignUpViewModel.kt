package com.trusov.sociallab.presentation.fragment.sing_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.domain.use_case.enter.LogInUseCase
import com.trusov.sociallab.domain.use_case.enter.SignUpUseCase
import javax.inject.Inject
import kotlin.math.log

class SignUpViewModel @Inject constructor(
    private val singUpUseCase: SignUpUseCase,
    private val logInUseCase: LogInUseCase
) : ViewModel() {

    private val _respondent = MutableLiveData<Respondent>()
    val respondent: LiveData<Respondent> = _respondent

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

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
            _message.value = MESSAGE_SUCCESS
            val respondent = logInUseCase(login, password1)
            if (respondent != null) {
                _respondent.value = respondent!!
            }
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
        if (!agreeWithTheTerms) {
            _message.value = MESSAGE_CONFIRM
            return false
        }
        return true
    }

    companion object {
        private const val MESSAGE_SUCCESS = "Регистрация прошла успешно"
        private const val MESSAGE_FILL_INPUTS = "Заполните все поля"
        private const val MESSAGE_PASSWORDS_DIFFERS = "Введённые пароли отличаются"
        private const val MESSAGE_CONFIRM = "Подтвердите согласие с условиями использования"
    }
}