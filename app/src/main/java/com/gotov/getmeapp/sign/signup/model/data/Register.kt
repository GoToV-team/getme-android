package com.gotov.getmeapp.sign.signup.model.data

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.regex.Pattern

data class Register(
    @JsonProperty("login") val login: String,
    @JsonProperty("password") val password: String
) {
    companion object {
        private val PASSWORD_PATTERN: Pattern = Pattern.compile(
            "^(?=.*\\d|[A-Z]|[a-z]|[^\\w\\d\\s:])([^\\s]){4,16}\$"
        )

        private const val EmptyField = "Поле должно быть не пустым"
        private const val IncorrectPassword = "Пароль может содержать только символы a-z, A-Z, 0-9, @#\$%^&+= \n b и быть длинной от 4 до 16 символов"

        fun validateLogin(login: String): String? {
            return if (login.isEmpty()) {
                EmptyField
            } else {
                null
            }
        }

        fun validatePassword(password: String): String? {
            return if (password.isEmpty()) {
                EmptyField
            } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
                IncorrectPassword
            } else {
                null
            }
        }
    }
}
