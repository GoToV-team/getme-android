package com.gotov.getmeapp.utils.ui

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setImage(link: String) {
    Glide.with(this).load(link).into(this)
}
