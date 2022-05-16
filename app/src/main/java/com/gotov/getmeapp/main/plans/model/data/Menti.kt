package com.gotov.getmeapp.main.plans.model.data

import android.widget.TextView

data class Menti(val name: String, val about: String) {
    fun addToViews(title: TextView?, description: TextView?) {
        title?.text = this.name
        description?.text = this.about
    }
}
