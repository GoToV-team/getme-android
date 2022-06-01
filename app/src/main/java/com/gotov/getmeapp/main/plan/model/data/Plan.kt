package com.gotov.getmeapp.main.plan.model.data

import android.widget.ProgressBar
import android.widget.TextView
import com.fasterxml.jackson.annotation.JsonProperty

data class Plan(
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("progress") val progress: Int,
    @JsonProperty("tasks") val tasks: List<Task>?,
    @JsonProperty("user") val user: User?,
) {
    fun addToViews(
        title: TextView?,
        description: TextView?,
        progress: ProgressBar?,
    ) {
        title?.text = this.title
        description?.text = this.description
        progress?.progress = this.progress
    }
}
