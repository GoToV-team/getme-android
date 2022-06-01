package com.gotov.getmeapp.main.plan.model.data

import com.fasterxml.jackson.annotation.JsonProperty

data class TaskResponse(
    @JsonProperty("task_id") val id: Int
)
