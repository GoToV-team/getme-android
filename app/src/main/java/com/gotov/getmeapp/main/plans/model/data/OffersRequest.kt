package com.gotov.getmeapp.main.plans.model.data

import com.fasterxml.jackson.annotation.JsonProperty

data class OffersRequest(
    @JsonProperty("description") val description: String,
    @JsonProperty("skills") val skills: List<String>,
    @JsonProperty("title") val title: String,
)
