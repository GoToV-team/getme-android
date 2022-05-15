package com.gotov.getmeapp.main_module.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.databinding.FragmentPlanTaskBinding
import com.gotov.getmeapp.main_module.task.data.getTasks

class TaskFragment : Fragment() {
    private val binding by viewBinding(FragmentPlanTaskBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val taskId = arguments?.getInt("task_id")
        val task = taskId?.let { getTasks().get(taskId)}
        task?.addToViews(binding.taskItemTitle, binding.taskItemDescription, binding.taskItemCheckbox)
        return binding.root
    }
}
