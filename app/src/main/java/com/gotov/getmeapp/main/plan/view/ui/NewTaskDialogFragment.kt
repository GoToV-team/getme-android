package com.gotov.getmeapp.main.plan.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentCreateTaskBinding
import com.gotov.getmeapp.main.plan.model.data.TaskCreate
import com.gotov.getmeapp.main.plan.viewmodel.PlanViewModel
import com.gotov.getmeapp.utils.ui.fieldEmptyError
import com.gotov.getmeapp.utils.ui.setOnEmptyError
import org.joda.time.Instant
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.Locale

class NewTaskDialogFragment(
    private val planId: Int,
    private val planViewModel: PlanViewModel
) : DialogFragment() {
    private var _binding: FragmentCreateTaskBinding? = null
    private val binding get() = _binding!!

    private var selectedTime: Long = MaterialDatePicker.todayInUtcMilliseconds()
    private var startTime: Long = selectedTime

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateTaskBinding.bind(
            inflater.inflate(
                R.layout.fragment_create_task,
                container
            )
        )
        return _binding!!.root
    }

    override fun onResume() {
        super.onResume()
        selectedTime = MaterialDatePicker.todayInUtcMilliseconds()
        startTime = selectedTime
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newTaskDataInput.isFocusable = false
        binding.newTaskDataInput.isLongClickable = false
        setupClickListeners()
    }

    private fun setDataPicker() {
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберете дедлайн задачи")
            .setSelection(selectedTime)
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        picker.addOnPositiveButtonClickListener {
            selectedTime = it
            val date = Instant.ofEpochMilli(selectedTime).toDateTime()
            val fmt: DateTimeFormatter = DateTimeFormat.forPattern("dd MM yyyy")
            fmt.withLocale(Locale.GERMAN)
            fmt.print(date)
            binding.newTaskDataInput.setText(fmt.print(date))
        }

        picker.show(childFragmentManager, "DataPicker")
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupClickListeners() {
        binding.newTaskCreateButton.setOnClickListener {
            val deadline = Instant.ofEpochMilli(selectedTime).toDateTime()
            val title = binding.newTaskTitleInput.text.toString()
            val about = binding.newTaskAboutInput.text.toString()

            if (title.isNotEmpty() && about.isNotEmpty() && selectedTime != startTime) {
                planViewModel.addTask(planId, TaskCreate(title, about, deadline))
                dismiss()
            } else {
                if (selectedTime == startTime) {
                    binding.newTaskDataInput.error = fieldEmptyError
                }
            }
        }

        binding.newTaskDataInput.setOnClickListener {
            setDataPicker()
        }

        binding.newTaskAboutInput.setOnEmptyError()
        binding.newTaskTitleInput.setOnEmptyError()
    }
}
