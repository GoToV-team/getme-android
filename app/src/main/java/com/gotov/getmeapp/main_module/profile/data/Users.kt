package com.gotov.getmeapp.main_module.profile.data

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.gotov.getmeapp.R

data class User(val id: Int, val name: String, val about: String, val skills: Array<String>) {
    private fun getChipSkill(skill: String, context: Context): Chip {
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
