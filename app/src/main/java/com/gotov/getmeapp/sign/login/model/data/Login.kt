package com.gotov.getmeapp.sign.login.model.data

import com.fasterxml.jackson.annotation.JsonProperty

data class Login(
    @JsonProperty("login") val login: String,
    @JsonProperty("password") val password: String
) {
    companion object {
        private const val EmptyField = "Поле должно быть не пустым"

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
            } else {
                null
            }
        }
    }
}
