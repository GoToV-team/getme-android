package com.gotov.getmeapp.main.search.model.data

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.gotov.getmeapp.R
import com.squareup.picasso.Picasso

data class User(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("about") val about: String,
    @JsonProperty("avatar") val avatar: String,
    @JsonProperty("skills") val skills: List<String>
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
        skills: ChipGroup?,
        image: ImageView?,
        context: Context?
    ) {
        title?.text = this.name
        description?.text = this.about

        if (avatar.isNotEmpty()) {
            image?.let { Picasso.get().load(avatar).into(it) }
        }

        if (skills != null && context != null) {
            for (skill in this.skills) {
                skills.addView(getChipSkill(skill, context))
            }
        }
    }
}
