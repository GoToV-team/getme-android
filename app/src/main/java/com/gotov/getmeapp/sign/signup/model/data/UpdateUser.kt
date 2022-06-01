package com.gotov.getmeapp.sign.signup.model.data

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateUser(
    @get:JsonProperty("first_name") val firstName: String,
    @get:JsonProperty("last_name") val lastName: String,
    @get:JsonProperty("about") val about: String = "",
    @get:JsonProperty("tg_tag") val TgTag: String,
    @get:JsonProperty("skills") val skills: List<String>
)
