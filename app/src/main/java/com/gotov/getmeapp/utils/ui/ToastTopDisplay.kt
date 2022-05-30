package com.gotov.getmeapp.utils.ui

import android.content.Context
import android.view.Gravity
import android.widget.Toast

fun displayToastOnTop(context: Context?, text: String, duration: Int) {
    val toast = Toast.makeText(context, text, duration)
    toast.setGravity(Gravity.TOP or Gravity.CENTER, 0, 0)
    toast.show()
}