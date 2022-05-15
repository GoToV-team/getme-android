package com.gotov.getmeapp.main_module.plans.data

import android.widget.TextView

data class Menti(val name: String, val about: String) {
    fun addToViews(title: TextView?, description: TextView?) {
        title?.text = this.name
        description?.text = this.about
    }
}
