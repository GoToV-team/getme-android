package com.gotov.getmeapp

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

data class User(val id: Int, val name: String, val about: String, val skills: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (name != other.name) return false
        if (about != other.about) return false
        if (!skills.contentEquals(other.skills)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + about.hashCode()
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

    fun addToViews(title: TextView?, description: TextView?, skills: ChipGroup?, context: Context?) {
        title?.text = this.name
        description?.text = this.about
        if (skills != null && context != null) {
            for (skill in this.skills) {
                skills.addView(getChipSkill(skill, context))
            }
        }
    }
}

fun getUsers() : Array<User>  {
    return arrayOf(
        User(0,"Dore", "Полезная задача",  Array(1) { "Мир" }),
        User(1,"Dore", "Поможет",  Array(1) { "Мир" }),
        User(2,"Dore", "Поможет",  Array(1) { "Мир" }),
        User(3,"Dore", "Поможет",  Array(1) { "Мир" }),
        User(4,"Dore", "Поможет",  Array(1) { "Мир" })
    )
}