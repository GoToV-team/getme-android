package com.gotov.getmeapp.main.plans.model.data

import com.fasterxml.jackson.annotation.JsonProperty

data class SkillResponseItem(
    @JsonProperty("name") val name: String
)

data class SkillResponse(
    @JsonProperty("skills") val skills: List<SkillResponseItem>
)
