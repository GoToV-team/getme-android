package com.gotov.getmeapp.sign.signup.model.data

import com.fasterxml.jackson.annotation.JsonProperty

data class Register(
    @JsonProperty("login") val login: String,
    @JsonProperty("password") val password: String
)
