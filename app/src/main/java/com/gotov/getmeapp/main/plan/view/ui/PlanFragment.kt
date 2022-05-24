package com.gotov.getmeapp.main.plan.view.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentPlanBinding
import com.gotov.getmeapp.main.plan.view.items.TaskViewAdapter
import com.gotov.getmeapp.main.plan.viewmodel.PlanViewModel
import com.gotov.getmeapp.utils.model.Resource
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlanFragment : Fragment(R.layout.fragment_plan) {

    private val binding by viewBinding(FragmentPlanBinding::bind)
    private val planViewModel by viewModel<PlanViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rec: RecyclerView = binding.taskList
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context)
        rec.layoutManager = layoutManager
        rec.adapter = TaskViewAdapter(listOf())

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            planViewModel.plan.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.addToViews(
                            binding.planTitle,
                            binding.planDescription,
                            binding.progressBar,
                            binding.planSkills,
                            context
                        )

                        binding.userInfoSmallName.text = it.data?.mentor?.name
                        binding.userInfoSmallNote.text = it.data?.mentor?.about

                        binding.taskList.adapter = it.data?.tasks?.let {
                            it1 ->
                            TaskViewAdapter(it1)
                        }

                        binding.taskList.visibility = View.VISIBLE
                        binding.spinner.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        binding.taskList.visibility = View.GONE
                        binding.spinner.visibility = View.VISIBLE
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val planId = arguments?.getInt("plan_id")

        planId?.let {
            planViewModel.getPlanById(it)
        }
    }
}
