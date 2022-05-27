package com.gotov.getmeapp.sign.signup.model.data

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.regex.Pattern

data class Register(
    @JsonProperty("login") val login: String,
    @JsonProperty("password") val password: String
) {
    companion object {
        private val PASSWORD_PATTERN: Pattern = Pattern.compile(
            "^" +  //"(?=.*[0-9])" +         //at least 1 digit
                    // "(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z0-9])" +  //any letter
                    "(?=.*[@#$%^&+=])" +  //at least 1 special character
                    "(?=\\S+$)" +  //no white spaces
                    ".{4,}" +  //at least 4 characters
                    "$"
        )

        private const val EmptyField = "Поле должно быть не пустым"
        private const val IncorrectPassword = "Пароль может содержать только символы a-z, A-Z, 0-9, @#\$%^&+="

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
