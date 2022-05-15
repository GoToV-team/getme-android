package com.gotov.getmeapp.main_module.plan.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.main_module.task.data.Task
import com.gotov.getmeapp.main_module.plan.items.TaskViewAdapter
import com.gotov.getmeapp.databinding.FragmentPlanBinding
import com.gotov.getmeapp.main_module.plan.data.getPlans
import com.gotov.getmeapp.main_module.task.data.getTasks

class PlanFragment : Fragment() {

    private val binding by viewBinding(FragmentPlanBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val test: Array<Task> = getTasks()
        val planId = arguments?.getInt("plan_id")
        val plan = planId?.let { getPlans().get(planId)}
        plan?.addToViews(binding.planTitle, binding.planDescription,
            binding.progressBar, binding.planSkills, this.context)

        binding.userInfoSmallName.text = "Ваcя Пупкин"
        binding.userInfoSmallNote.text = "Ментор"

        val rec: RecyclerView = binding.taskList
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context)
        rec.layoutManager = layoutManager
        rec.adapter = TaskViewAdapter(test)

        return binding.root
    }
}
