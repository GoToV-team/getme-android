package com.gotov.getmeapp.main.plan.model.data

import android.widget.CheckBox
import android.widget.TextView
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.Locale

data class TaskCreate(
    @get:JsonProperty("name") val name: String,
    @get:JsonProperty("description") val description: String,
    @get:JsonProperty("deadline")
    @get:JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        timezone = "UTC"
    )
    val deadline: DateTime,
)

data class Task(
    @JsonProperty("id") val id: Int,
    @JsonProperty("title") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("deadline")
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
        timezone = "UTC"
    )
    val deadline: DateTime,
    @JsonProperty("status") val status: String,
) {
    companion object {
        private const val doneTask = "Выполнена"
    }

    fun addToViews(title: TextView?, description: TextView?, checkBox: CheckBox?, date: TextView?) {
        title?.text = this.name
        description?.text = this.description
        checkBox?.isChecked = this.status == doneTask

        if (this.status == doneTask) {
            checkBox?.isClickable = false
        }

        val fmt: DateTimeFormatter = DateTimeFormat.forPattern("dd.mm.yyyy")
        fmt.withLocale(Locale.ROOT)
        date?.text = fmt.print(deadline.toLocalDateTime())
    }
}
