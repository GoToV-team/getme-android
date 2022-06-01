package com.gotov.getmeapp.utils.ui

import android.widget.EditText
import androidx.core.widget.doOnTextChanged

const val fieldEmptyError = "Поле не должно быть пустым"

fun EditText.setOnEmptyError(
    addingAction: (
        text: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) -> Unit = { _, _, _, _ -> }
) {
    this.doOnTextChanged { text, start, before, count ->
        if (text != null) {
            if (text.isEmpty()) {
                this.error = fieldEmptyError
            } else {
                this.error = null
            }
        } else {
            this.error = fieldEmptyError
        }
        addingAction(text, start, before, count)
    }
}
