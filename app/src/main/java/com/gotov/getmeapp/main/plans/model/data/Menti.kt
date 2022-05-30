package com.gotov.getmeapp.main.plans.model.data

import android.widget.ImageView
import android.widget.TextView
import com.fasterxml.jackson.annotation.JsonProperty
import com.gotov.getmeapp.utils.ui.setImage

data class Mentis(
    @JsonProperty("users") val mentis: List<Menti>
)


data class Menti(
    @JsonProperty("id") val id: Int,
    @JsonProperty("offer_id") val offerId: Int,
    @JsonProperty("first_name") val firstName: String?,
    @JsonProperty("last_name") val lastName: String?,
    @JsonProperty("about") val about: String?,
    @JsonProperty("avatar") val avatar: String?,
    @JsonProperty("isMentor") val isMentor: Boolean,
) {

    fun addToViews(title: TextView?, description: TextView?, image: ImageView?) {
        val tmp = "${this.firstName} ${this.lastName}"
        title?.text = tmp
        description?.text = this.about
        this.avatar?.let {
            image?.setImage(it)
        }
    }
}
