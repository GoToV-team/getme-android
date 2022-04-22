package com.gotov.getmeapp.data

import android.widget.CheckBox
import android.widget.TextView

data class Task(val id: Int, val name: String, val about : String, val isDone: Boolean ) {
    fun addToViews(title: TextView?, description: TextView?,  checkBox: CheckBox?) {
        title?.text = this.name
        description?.text = this.about
        checkBox?.isChecked = this.isDone
    }
}

fun getTasks() : Array<Task>  {
    return arrayOf(
        Task(0,"Dore", "Купить", true),
        Task(1,"Dore", "Купить", true),
        Task(2,"Dore", "Купить", true),
        Task(3,"Dore", "Купить", true),
        Task(4,"Dore", "Купить", true)
    )
}
