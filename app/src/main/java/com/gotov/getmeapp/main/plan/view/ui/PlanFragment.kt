package com.gotov.getmeapp.main.plan.view.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentPlanBinding
import com.gotov.getmeapp.main.plan.model.data.Task
import com.gotov.getmeapp.main.plan.view.items.TaskViewAdapter
import com.gotov.getmeapp.main.plan.viewmodel.PlanViewModel
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.model.removeTgTag
import com.gotov.getmeapp.utils.ui.DiffUtilsCallback
import com.gotov.getmeapp.utils.ui.displayToastOnTop
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlanFragment : Fragment(R.layout.fragment_plan) {

    companion object {
        private val tgUrl = "https://t.me/"
    }

    private val binding by viewBinding(FragmentPlanBinding::bind)
    private val planViewModel by viewModel<PlanViewModel>()

    private var isMentor: Boolean? = false
    private var planId: Int? = 0

    private var adapter: TaskViewAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runLifeCycles()

        binding.planAddTaskButton.setOnClickListener {
            showAddTaskDialog()
        }

        binding.planPageSwipeLayout.setOnRefreshListener {
            planId?.let { planId ->
                planViewModel.getPlanById(planId)
                binding.planPageSwipeLayout.isRefreshing = true
            }
        }
    }

    private fun setTaskRecycleView() {
        binding.taskList.layoutManager = LinearLayoutManager(context)
        adapter?.let {
            binding.taskList.adapter = it
        } ?: run {
            adapter = TaskViewAdapter(arrayListOf()) {
                if (this.isMentor == true) {
                    planViewModel.applyTask(it.id)
                }
            }
            binding.taskList.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        planId = arguments?.getInt("plan_id")
        isMentor = arguments?.getBoolean("is_mentor")
        planId?.let {
            planViewModel.getPlanById(it)
        }

        if (isMentor != true) {
            binding.planAddTaskButton.visibility = View.GONE
        }

        setTaskRecycleView()
    }

    private fun runLifeCycles() {
        runStatusCreatingLifeCycle()
        runStatusApplyingLifeCycle()
        runStatusPlanLifeCycle()
    }

    private fun runStatusCreatingLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            planViewModel.statusCreating.collect {
                when (it) {
                    is Resource.Success -> {
                        planId?.let { planId ->
                            planViewModel.getPlanById(planId)
                        }
                        planViewModel.setNullStatusCreating()
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        displayToastOnTop(
                            context,
                            "Произошла ошибка изменение статуса задачи ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                        planViewModel.setNullStatusCreating()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun runStatusApplyingLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            planViewModel.statusApplying.collect {
                when (it) {
                    is Resource.Success -> {
                        planId?.let { planId ->
                            planViewModel.getPlanById(planId)
                        }

                        displayToastOnTop(
                            context,
                            "Задача успешно добавлена",
                            Toast.LENGTH_SHORT
                        )
                        planViewModel.setNullStatusApplying()
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        displayToastOnTop(
                            context,
                            "Произошла ошибка добавления задачи ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                        planViewModel.setNullStatusApplying()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun runStatusPlanLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            planViewModel.plan.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { plan ->
                            plan.user?.addToViews(
                                binding.userInfoSmallName,
                                null,
                                binding.userInfoSmallAvatar,
                                binding.userInfoSmallNote
                            )

                            binding.mentiInfoSendButton.setOnClickListener { _ ->
                                plan.user?.let { user ->
                                    val browserIntent =
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("$tgUrl/${user.TgTag.removeTgTag()}")
                                        )
                                    startActivity(browserIntent)
                                }
                            }

                            binding.planTitle.text = plan.title
                            binding.planDescription.text = plan.description
                            binding.planProgressBar.progress = plan.progress

                            plan.tasks?.let { tasks ->
                                changeAdapter(tasks)
                            }
                        }
                        stopLoadingPage()
                    }
                    is Resource.Loading -> {
                        startLoadingPage()
                    }
                    is Resource.Error -> {
                        displayToastOnTop(
                            context,
                            "Произошла ошибка получения пользователя ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                        stopLoadingPage()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun startLoadingPage() {
        binding.planTitle.visibility = View.GONE
        binding.planProgressBar.visibility = View.GONE
        binding.planDescription.visibility = View.GONE
        binding.mentiInfoSendButton.visibility = View.GONE
        binding.userInfoSmallAvatar.visibility = View.GONE
        binding.userInfoSmallName.visibility = View.GONE
        binding.userInfoSmallNote.visibility = View.GONE
        binding.planAddTaskButton.visibility = View.GONE
        binding.planPageSwipeLayout.isRefreshing = true
    }

    private fun stopLoadingPage() {
        binding.planTitle.visibility = View.VISIBLE
        binding.planProgressBar.visibility = View.VISIBLE
        binding.planDescription.visibility = View.VISIBLE
        binding.mentiInfoSendButton.visibility = View.VISIBLE
        binding.userInfoSmallAvatar.visibility = View.VISIBLE
        binding.userInfoSmallName.visibility = View.VISIBLE
        binding.userInfoSmallNote.visibility = View.VISIBLE
        if (isMentor == true) {
            binding.planAddTaskButton.visibility = View.VISIBLE
        }
        binding.planPageSwipeLayout.isRefreshing = false
    }

    private fun changeAdapter(tasks: List<Task>) {
        if (adapter != null) {
            val userDiffUtilCallback =
                DiffUtilsCallback(
                    adapter!!.getData(),
                    tasks,
                    { oldItem: Task, newItem: Task ->
                        oldItem.id == newItem.id
                    },
                    { oldItem: Task, newItem: Task ->
                        oldItem.name == newItem.name &&
                            oldItem.description == newItem.description &&
                            oldItem.deadline == newItem.deadline &&
                            oldItem.status == newItem.status
                    }
                )
            val productDiffResult = DiffUtil.calculateDiff(userDiffUtilCallback)

            adapter!!.setData(tasks)
            productDiffResult.dispatchUpdatesTo(adapter!!)
        }
    }

    private fun showAddTaskDialog() {
        planId?.let {
            val newFragment = NewTaskDialogFragment(it, planViewModel)
            newFragment.show(childFragmentManager, "add_task")
        }
    }
}
