package com.gotov.getmeapp.main.search.model.data

import com.fasterxml.jackson.annotation.JsonProperty

data class Skill(val name: String, var active: Boolean)

data class SkillResponse(
    @JsonProperty("login") val name: String
)