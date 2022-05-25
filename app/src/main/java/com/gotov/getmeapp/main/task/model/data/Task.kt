package com.gotov.getmeapp.main.task.model.data

import android.widget.CheckBox
import android.widget.TextView
import com.fasterxml.jackson.annotation.JsonProperty

data class Task(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("about") val about: String,
    @JsonProperty("isDone") var isDone: Boolean,
    @JsonProperty("description") val description: String?

) {
    fun addToViews(title: TextView?, description: TextView?, checkBox: CheckBox?) {
        title?.text = this.name
        description?.text = this.about
        checkBox?.isChecked = this.isDone
    }
}

fun getTasks(): Array<Task> {
    return arrayOf(
        Task(0, "Dore", "Купить", true, null),
        Task(1, "Dore", "Купить", true, null),
        Task(2, "Dore", "Купить", true, null),
        Task(3, "Dore", "Купить", true, null),
        Task(4, "Dore", "Купить", true, null)
    )
}
