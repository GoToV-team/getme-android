package com.gotov.getmeapp.main.task.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentPlanTaskBinding
import com.gotov.getmeapp.main.task.model.data.getTasks

class TaskFragment : Fragment(R.layout.fragment_plan_task) {
    private val binding by viewBinding(FragmentPlanTaskBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val taskId = arguments?.getInt("task_id")
        val task = taskId?.let { getTasks()[taskId] }
        task?.addToViews(binding.taskItemTitle, binding.taskItemDescription, binding.taskItemCheckbox)
        return binding.root
    }
}
