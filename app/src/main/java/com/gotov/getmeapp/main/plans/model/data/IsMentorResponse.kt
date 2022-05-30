package com.gotov.getmeapp.main.plans.model.data

import com.fasterxml.jackson.annotation.JsonProperty

data class IsMentorResponse(
    @JsonProperty("is_mentor") val isMentor: Boolean
)
