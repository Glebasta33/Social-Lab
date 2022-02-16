package com.trusov.sociallab.utils

object Constants {

    // SingUpUseCase
    const val PASSWORD_ARE_SAME = "Регистрация прошла успешно!"
    const val PASSWORD_ARE_NOT_SAME = "Пароли отличаются"

    // LogInUseCase
    const val USER_IS_REGISTERED = "Добро пожаловать!"
    const val USER_IS_NOT_REGISTERED = "Пользователь с ввёденными данными не зарегистрирован"

    const val UNKNOWN_ID = -1L
    const val UNKNOWN_DATE =  "01.01.1970"

    enum class Gender {
        MALE, FEMALE, NOT_SPECIFIED
    }
}