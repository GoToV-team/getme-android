package com.gotov.getmeapp.sign_module.login.model.data

import com.fasterxml.jackson.annotation.JsonProperty

data class Login(
    @JsonProperty("login") val login: String,
    @JsonProperty("login") val password: String
)
