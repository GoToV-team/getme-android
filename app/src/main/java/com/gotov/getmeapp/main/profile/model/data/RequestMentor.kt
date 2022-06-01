package com.gotov.getmeapp.main.profile.model.data

import com.fasterxml.jackson.annotation.JsonProperty

data class RequestMentor(
    @get:JsonProperty("mentor_id") var mentorId: Int,
    @get:JsonProperty("skill_name") var skillName: String
)
