package com.gotov.getmeapp.main.task.view.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentPlanTaskBinding
import com.gotov.getmeapp.main.task.viewmodel.TaskViewModel
import com.gotov.getmeapp.utils.model.Resource
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskFragment : Fragment(R.layout.fragment_plan_task) {
    private val binding by viewBinding(FragmentPlanTaskBinding::bind)

    private val taskViewModel by viewModel<TaskViewModel>()

    private var taskId: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.taskItemCheckbox.setOnCheckedChangeListener { _, b ->
            taskId?.let {
                taskViewModel.markTask(taskId!!)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            taskId = arguments?.getInt("task_id")
            taskId?.let { taskViewModel.getTask(it) }

            taskViewModel.task.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.addToViews(
                            binding.taskItemTitle,
                            binding.taskItemDescription,
                            binding.taskItemCheckbox
                        )
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> {
                    }
                    else -> {}
                }
            }
        }
    }
}
