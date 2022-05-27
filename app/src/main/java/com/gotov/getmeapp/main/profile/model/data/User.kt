package com.gotov.getmeapp.main.profile.model.data

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.gotov.getmeapp.R
import com.bumptech.glide.Glide

data class User(
    @JsonProperty("id") val id: Int,
    @JsonProperty("first_name")  val firstName: String? = "",
    @JsonProperty("last_name") val lastName: String? = "",
    @JsonProperty("about") val about: String? = "",
    @JsonProperty("avatar")  val avatar: String? = "",
    @JsonProperty("skills") val skills: List<String>,
    @JsonProperty("is_mentor") val isMentor: Boolean
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
        context: Context
    ) {
        title?.text = this.firstName.plus(' ').plus(this.lastName)
        description?.text = this.about

        if (avatar != null && avatar.isNotEmpty()) {
            image?.let { Glide.with(context).load(avatar).into(it) }
        }

        if (skills != null) {
            for (skill in this.skills) {
                skills.addView(getChipSkill(skill, context))
            }
        }
    }
}
