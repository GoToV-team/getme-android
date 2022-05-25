package com.gotov.getmeapp.main.search.model.data

import com.fasterxml.jackson.annotation.JsonProperty

data class Skill(val name: String, var active: Boolean)

data class SkillResponseItem(
    @JsonProperty("name") val name: String
)

data class SkillResponse(
    @JsonProperty("skills") val skills: List<SkillResponseItem>
)
