package com.gotov.getmeapp.main.plan.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.main.task.model.data.Task
import com.gotov.getmeapp.main.plan.view.items.TaskViewAdapter
import com.gotov.getmeapp.databinding.FragmentPlanBinding
import com.gotov.getmeapp.main.plan.model.data.getPlans
import com.gotov.getmeapp.main.task.model.data.getTasks

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
