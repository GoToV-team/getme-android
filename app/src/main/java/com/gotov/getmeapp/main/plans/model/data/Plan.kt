package com.gotov.getmeapp.main.plans.model.data

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.gotov.getmeapp.R

data class PlansResponse(
    @JsonProperty("plans") val plans: List<Plan>
)

data class Plan(
    @JsonProperty("skills") val skills: List<String>,
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val title: String,
    @JsonProperty("about") val description: String?,
    @JsonProperty("progress") val progress: Int,
    @JsonProperty("menti_id") val mentiId: Int,
) {
    companion object {
        private const val textSizeChip = 11F
    }

    private fun getChipSkill(skill: String, context: Context): Chip {
        val tmp = Chip(context)
        tmp.text = skill
        tmp.setChipBackgroundColorResource(R.color.teal_700)
        tmp.textAlignment = View.TEXT_ALIGNMENT_CENTER
        tmp.setTextColor(Color.WHITE)
        tmp.textSize = textSizeChip

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
            skills.removeAllViews()
            for (skill in this.skills) {
                skills.addView(getChipSkill(skill, context))
            }
        }
    }
}