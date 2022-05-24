package com.gotov.getmeapp.main.plan.model.data

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.gotov.getmeapp.R
import com.gotov.getmeapp.main.search.model.data.User
import com.gotov.getmeapp.main.task.model.data.Task

data class Plan(
    @JsonProperty("id") val id: Int,
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("progress") val progress: Int,
    @JsonProperty("skills") val skills: List<String>,
    @JsonProperty("tasks") val tasks: List<Task>,
    @JsonProperty("mentor") val mentor: User?,
    @JsonProperty("menti") val menti: User?,
) {
    private fun getChipSkill(skill: String, context: Context): Chip {
        val tmp = Chip(context)
        tmp.text = skill
        tmp.setChipBackgroundColorResource(R.color.teal_700)
        tmp.textAlignment = View.TEXT_ALIGNMENT_CENTER
        tmp.setTextColor(Color.WHITE)
        tmp.textSize = 11F

        return tmp
    }

    fun addToViews(
        title: TextView?,
        description: TextView?,
        progress: ProgressBar?,
        skills: ChipGroup?,
        context: Context?
    ) {
        title?.text = this.title
        description?.text = this.description
        progress?.progress = this.progress
        if (skills != null && context != null) {
            for (skill in this.skills) {
                skills.addView(getChipSkill(skill, context))
            }
        }
    }
}

fun getPlans(): Array<Plan> {
    return arrayOf(
        Plan(
            0,
            "Dore",
            "Полезная задача",
            20,
            List(1) { "Мир" },
            listOf(
                Task(1, "One task", "desc 1", false),
                Task(2, "Two task", "desc 2 done", true),
                Task(3, "Three task", "", false),
            ),
            User(0, "asd", "asd", "", listOf()),
            null

        ),
        Plan(
            1,
            "Another task",
            "sdfs dfsdf",
            20,
            List(1) { "Gore" },
            listOf(
                Task(1, "One task", "desc 1", false),
                Task(2, "Two task", "desc 2 done", true),
                Task(3, "Three task", "", false),
            ),
            User(0, "asd", "asd", "", listOf()),
            null
        )
    )
}
