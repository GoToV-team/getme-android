package com.gotov.getmeapp.data

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.gotov.getmeapp.R

data class Plan(val id: Int, val title: String, val description: String, val progress: Int, val skills: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Plan

        if (title != other.title) return false
        if (progress != other.progress) return false
        if (!skills.contentEquals(other.skills)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + progress.hashCode()
        result = 31 * result + skills.contentHashCode()
        return result
    }

    private fun getChipSkill(skill: String, context: Context) : Chip {
        val tmp = Chip(context)
        tmp.text = skill
        tmp.setChipBackgroundColorResource(R.color.teal_700)
        tmp.textAlignment = View.TEXT_ALIGNMENT_CENTER
        tmp.setTextColor(Color.WHITE)
        tmp.textSize = 11F

        return tmp
    }

    fun addToViews(title: TextView?, description: TextView?,
                   progress: ProgressBar?, skills: ChipGroup?,
                   context: Context?) {
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

fun getPlans() : Array<Plan>  {
    return arrayOf(
        Plan(0,"Dore", "Полезная задача", 20, Array(1) { "Мир" }),
        Plan(1,"Dore", "Поможет", 20, Array(1) { "Мир" }),
        Plan(2,"Dore", "Поможет", 20, Array(1) { "Мир" }),
        Plan(3,"Dore", "Поможет", 20, Array(1) { "Мир" }),
        Plan(4,"Dore", "Поможет", 20, Array(1) { "Мир" })
    )
}
