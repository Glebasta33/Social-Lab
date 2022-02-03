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

    private val _fragmentIsReadyToClose = MutableLiveData<Unit>()
    val fragmentIsReadyToClose: LiveData<Unit> = _fragmentIsReadyToClose

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
                _respondent.value = respondent!!
            } else {
                _message.value = "Неверный логин или пароль"
            }

//            when(resource) {
//                is Resource.Error -> {
//                    _message.value = resource.message!!
//                }
//                is Resource.Success -> {
//                    _respondent.value = resource.data ?: throw RuntimeException("Respondent == null")
//                    closeFragment()
//                }
//                is Resource.Loading -> {
//
//                }
//            }

        }

    }

    private fun parseInput(input: String?): String {
        return input?.trim() ?: ""
    }

    private fun validateInput(login: String, password: String): Boolean {
        var result = true
        if (login.isBlank() || password.isBlank()) {
            result = false
        }
            return result
    }

    private fun closeFragment() {
        _fragmentIsReadyToClose.value = Unit
    }
}