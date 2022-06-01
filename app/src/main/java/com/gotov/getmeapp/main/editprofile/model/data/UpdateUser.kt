package com.gotov.getmeapp.main.editprofile.model.data

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateUser(
    @get:JsonProperty("first_name") var firstName: String = "",
    @get:JsonProperty("last_name") var lastName: String = "",
    @get:JsonProperty("about") var about: String = "",
    @get:JsonProperty("tg_tag") var tgTag: String = "",
    @get:JsonProperty("skills") var skills: List<String> = arrayListOf()
)
