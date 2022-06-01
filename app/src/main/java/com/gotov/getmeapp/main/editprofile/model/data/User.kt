package com.gotov.getmeapp.main.editprofile.model.data

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.fasterxml.jackson.annotation.JsonProperty
import com.gotov.getmeapp.utils.ui.setImage

data class User(
    @JsonProperty("id") val id: Int,
    @JsonProperty("first_name") val firstName: String? = "",
    @JsonProperty("last_name") val lastName: String? = "",
    @JsonProperty("about") val about: String? = "",
    @JsonProperty("avatar") val avatar: String? = "",
    @JsonProperty("skills") val skills: List<String>,
    @JsonProperty("tg_tag") val tgTag: String,
    @JsonProperty("is_mentor") val isMentor: Boolean
) {
    companion object {
        const val MentorText = "Ментор"
        const val MentiText = "Менти"
    }

    @SuppressLint("SetTextI18n")
    fun addToViews(
        firstName: TextView?,
        secondName: TextView?,
        description: TextView?,
        tgTag: TextView?,
        image: ImageView?,
        isMentor: SwitchCompat?
    ) {
        firstName?.text = this.firstName
        secondName?.text = this.lastName
        description?.text = this.about
        tgTag?.text = this.tgTag
        isMentor?.isChecked = this.isMentor

        if (this.isMentor) {
            isMentor?.text = MentorText
        } else {
            isMentor?.text = MentiText
        }

        if (avatar != null && avatar.isNotEmpty()) {
            image?.setImage(avatar)
        }
    }
}
