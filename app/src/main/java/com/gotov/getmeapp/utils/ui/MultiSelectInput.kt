package com.gotov.getmeapp.utils.ui

import android.graphics.Rect
import android.text.Spanned
import android.text.style.ImageSpan
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.gotov.getmeapp.R

class MultiSelectInput(
    private val editText: TextInputEditText,
    private val skills: ArrayList<String>,
    private val isSelectedSkills: ArrayList<Boolean>
) {
    fun setupClickListeners() {
        editText.isFocusable = false
        editText.isLongClickable = false

        addChips()

        editText.setOnClickListener {
            editText.context?.let { ctx ->
                MaterialAlertDialogBuilder(ctx)
                    .setTitle(editText.resources.getString(R.string.select_skills_title))
                    .setPositiveButton(editText.resources.getString(R.string.hide)) { it, _ ->
                        it.dismiss()
                    }
                    .setMultiChoiceItems(
                        skills.toTypedArray(),
                        isSelectedSkills.toBooleanArray()
                    ) { _, which, checked ->
                        isSelectedSkills[which] = checked
                        addChips()
                    }
                    .show()
            }
        }
    }

    fun getSelectedSkills(): List<String> {
        return skills.filterIndexed { i, _ -> isSelectedSkills[i] }
    }

    private fun addChips() {
        var res = ""
        val positions: MutableList<Int> = arrayListOf()
        skills.forEachIndexed { i, s ->
            positions.add(res.length)
            if (isSelectedSkills[i]) {
                res += s
            }
        }

        editText.setText(res)
        isSelectedSkills.forEachIndexed { i, select ->
            if (select) {
                val chip = ChipDrawable.createFromResource(editText.context, R.xml.chip)
                chip.isCheckable = false
                chip.isCloseIconVisible = false
                chip.text = skills[i]
                chip.bounds = Rect(0, 0, chip.intrinsicWidth, chip.intrinsicHeight)
                editText.text?.setSpan(
                    ImageSpan(chip),
                    positions[i],
                    positions[i] + skills[i].length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }
}
