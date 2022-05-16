package com.gotov.getmeapp.sign.login.model.data

import com.fasterxml.jackson.annotation.JsonProperty

data class Login(
    @JsonProperty("login") val login: String,
    @JsonProperty("login") val password: String
)
