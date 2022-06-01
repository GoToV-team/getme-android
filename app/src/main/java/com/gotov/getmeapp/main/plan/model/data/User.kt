package com.gotov.getmeapp.main.plan.model.data

import android.widget.ImageView
import android.widget.TextView
import com.fasterxml.jackson.annotation.JsonProperty
import com.gotov.getmeapp.utils.ui.setImage

data class User(
    @JsonProperty("id") val id: Int,
    @JsonProperty("first_name") val firstName: String? = "",
    @JsonProperty("last_name") val lastName: String? = "",
    @JsonProperty("about") val about: String? = "",
    @JsonProperty("tg_tag") val TgTag: String = "",
    @JsonProperty("avatar") val avatar: String? = "",
    @JsonProperty("is_mentor") val isMentor: Boolean
) {
    companion object {
        private const val menti = "Менти"
        private const val mentor = "Ментор"
    }
    fun addToViews(
        title: TextView?,
        description: TextView?,
        image: ImageView?,
        roleInfo: TextView?,
    ) {
        val tmp = "${this.firstName} ${this.lastName}"
        title?.text = tmp
        description?.text = this.about

        if (isMentor) {
            roleInfo?.text = mentor
        } else {
            roleInfo?.text = menti
        }

        if (avatar != null && avatar.isNotEmpty()) {
            image?.setImage(avatar)
        }
    }
}
